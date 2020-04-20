package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.support.feature.EmailData;

public interface IMailService {
    void sendMail(EmailData emailData);
}
