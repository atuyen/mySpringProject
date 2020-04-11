package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Invoice;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IInvoiceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@Service
public class InvoiceRepository extends BaseRepository<Invoice> implements IInvoiceRepository {
}
