package com.seasolutions.stock_management.service.implementations;


import com.seasolutions.stock_management.model.entity.Invoice;
import com.seasolutions.stock_management.model.view_model.InvoiceDetailViewModel;
import com.seasolutions.stock_management.model.view_model.InvoiceViewModel;
import com.seasolutions.stock_management.repository.interfaces.IInvoiceRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class InvoiceService  extends BaseService<Invoice, InvoiceViewModel> implements IInvoiceService {
    @Autowired
    IInvoiceRepository invoiceRepository;

    @Override
    public List<InvoiceDetailViewModel> test() {
        return null;
    }
}
