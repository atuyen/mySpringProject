package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.InvoiceDetail;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IInvoiceDetailRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class InvoiceDetailRepository extends BaseRepository<InvoiceDetail> implements IInvoiceDetailRepository {
}
