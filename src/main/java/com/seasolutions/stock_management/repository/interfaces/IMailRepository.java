package com.seasolutions.stock_management.repository.interfaces;

import com.seasolutions.stock_management.model.entity.SentEmail;
import com.seasolutions.stock_management.repository.base.IBaseRepository;

import java.util.List;

public interface IMailRepository extends IBaseRepository<SentEmail> {
    List<SentEmail> getUnsentEmails(int  maxFailedCount, int limit);
}
