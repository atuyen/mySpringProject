package com.seasolutions.stock_management.controller;

import com.seasolutions.stock_management.model.support.feature.EmailData;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import com.seasolutions.stock_management.service.implementations.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {


    @Autowired
    MailService mailService;

    @Unauthenticated
    @PostMapping(path = "/send-mail")
    public void sendMail(EmailData emailData) {
        mailService.sendMail(emailData);
    }


}


