package com.seasolutions.stock_management.service.implementations.mail;

import com.seasolutions.stock_management.model.entity.EmailFile;
import com.seasolutions.stock_management.model.entity.SentEmail;
import com.seasolutions.stock_management.model.support.enumerable.LanguageType;
import com.seasolutions.stock_management.model.support.enumerable.MailTemplateType;
import com.seasolutions.stock_management.model.support.feature.EmailData;
import com.seasolutions.stock_management.repository.interfaces.IMailRepository;
import com.seasolutions.stock_management.service.implementations.ThymeleafService;
import com.seasolutions.stock_management.service.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class MailService implements IMailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";




    @Autowired
    ThymeleafService thymeleafService;

    @Autowired
    IMailRepository mailRepository;

    @Override
    public void sendMail(EmailData emailData){



        Map<String,Object> params = new HashMap<>();
        params.put("name", "Messi");
        params.put("project_name", "spring-email-with-thymeleaf Demo");
        String template =  thymeleafService.getContent(MailTemplateType.SEND_ADMIN, LanguageType.En,params);


        List<EmailFile> emailFileList = emailData.getFiles().stream().map(f-> {
            try {
                return EmailFile.builder()
                        .name(f.getOriginalFilename())
                        .fileContent(f.getBytes())
                        .build();
            } catch (IOException e) {
               return  null;
            }
        }).collect(Collectors.toList());


        SentEmail sentEmail = SentEmail.builder()
                .recipientAddress("tuyen.nguyen@sea-solutions.com")
                .subject("Subject test")
                .message(template)
                .build();
        emailFileList.forEach(emailFile -> {
            sentEmail.addEmailFile(emailFile);
        });

        mailRepository.add(sentEmail);
    }








}
