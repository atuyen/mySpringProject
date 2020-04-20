package com.seasolutions.stock_management.service.implementations.mail;


import com.seasolutions.stock_management.model.support.enumerable.MailSentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.function.Function;

@Component
public class CustomMailSender {
    @Value("${email.enabled:true}")
    boolean enabled;


    @Autowired
    JavaMailSender sender;





    public MimeMessage createMessage(){
        return  sender.createMimeMessage();
    }



    public void send(MimeMessage message, Function<MailSentStatus, Void> callback){
        try{
            if(enabled){
                sender.send(message);
                callback.apply(MailSentStatus.SUCCESS);
            }
        }catch (Throwable t){
            String errorMsg = t.getMessage();
            if(isInvalidAddress(errorMsg)){
                callback.apply(MailSentStatus.INVALID_ADDRESS);
                return;
            }
            if (errorMsg.contains("SocketTimeoutException") || errorMsg.contains("MailConnectException")) {
                callback.apply(MailSentStatus.TIMEOUT_ERROR);
                return;
            }

            callback.apply(MailSentStatus.OTHER_ERROR);

        }



    }



    private Boolean isInvalidAddress(String errorMessage) {
        return errorMessage.contains("550 unroutable domain") ||
                errorMessage.contains("550.+previously hard-bounced") ||
                errorMessage.contains("551 That looks like a typo, try");
    }









}
