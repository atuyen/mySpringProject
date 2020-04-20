package com.seasolutions.stock_management.service.implementations.mail;


import com.seasolutions.stock_management.model.entity.EmailFile;
import com.seasolutions.stock_management.model.entity.SentEmail;
import com.seasolutions.stock_management.model.support.enumerable.MailSentStatus;
import com.seasolutions.stock_management.repository.interfaces.IMailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@Log4j2
@Component
@Transactional
public class MailSenderJob {

    @Value("${email.defaultFromAddress}")
    private String defaultFromMailAddress;
    @Value("${email.defaultFromNameSystem}")
    private String defaultFromName;
    @Value("${email.maxRetryCount}")
    private int maxRetryCount;
    @Value("${email.resendInterval}")
    private int resendIntervalInMs;
    @Value("${email.nrOfWorkerThreads:10}")
    private int nrOfWorkerThreads;

    @Value("${email.sendMailInterval}")
    private static final String sendMailInterval = "10000";


    @Autowired
    IMailRepository mailRepository;


    @Autowired
    private CustomMailSender mailSender;


    private ExecutorService executorService = null;


    @PostConstruct
    private void init() {
        this.executorService = Executors.newFixedThreadPool(nrOfWorkerThreads);
    }

    @PreDestroy
    private void destroy() {
        this.executorService.shutdown();
    }


    @Scheduled(initialDelay = 15000, fixedDelayString = sendMailInterval)
    void runSendEmails() {
        int totalMailSent = 0;
        try {
            List<SentEmail> unsentEmails = mailRepository.getUnsentEmails(maxRetryCount, 25);
            if (unsentEmails.size() == 0) {
                return;
            }
            totalMailSent += sendEmailChunk(unsentEmails);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        if (totalMailSent > 0) {
            log.debug("Tried to send/resend " + totalMailSent + " mails");
        }


    }


    private int sendEmailChunk(List<SentEmail> mails) throws InterruptedException {
        List<Callable<Void>> tasks = mails.stream().map(m -> new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sendMail(m);
                return null;
            }
        }).collect(Collectors.toList());
        List<Future<Void>> futures = executorService.invokeAll(tasks);
        futures.forEach(it -> {
            try {
                it.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        return futures.size();
    }


    private void sendMail(SentEmail mail) throws UnsupportedEncodingException, MessagingException {
        AtomicBoolean deleted = new AtomicBoolean(false);

        try {
            MimeMessage message = mailSender.createMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(defaultFromMailAddress, defaultFromName);
            messageHelper.setTo(mail.getRecipientAddress());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
            addAttachments(messageHelper,mail.getEmailFiles());
            mailSender.send(message, mailSentStatus -> {
                if(mailSentStatus==MailSentStatus.SUCCESS){
                    if(!mail.getKeepHistory()){
                        mailRepository.delete(mail);
                        deleted.set(true);
                    }else {
                        mail.setSent(true);
                        mail.setDateSent(new Date());
                    }
                }else {
                    mail.setFailedCount(mail.getFailedCount()+1);
                    if(mailSentStatus==MailSentStatus.INVALID_ADDRESS){
                        mail.setInvalidAddress(true);
                    }
                }

                if(!deleted.get()){
                    mailRepository.update(mail);
                }

                return null;
            });



        } catch (Throwable e) {
           if(!deleted.get()){
               mail.setFailedCount(mail.getFailedCount()+1);
               mail.setLastTry(new Date());
               mailRepository.update(mail);
           }
           throw  e;
        }


    }

    private void addAttachments(MimeMessageHelper helper, List<EmailFile>  attachments) {
        if (attachments!=null) {
            attachments.forEach(a->{
                ByteArrayResource attachmentFile = new ByteArrayResource(a.getFileContent());
                try {
                    helper.addAttachment(a.getName(),attachmentFile);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }









}
