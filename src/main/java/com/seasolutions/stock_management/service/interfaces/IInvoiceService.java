package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.Invoice;
import com.seasolutions.stock_management.model.view_model.InvoiceDetailViewModel;
import com.seasolutions.stock_management.model.view_model.InvoiceViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;

import java.util.List;

public interface IInvoiceService extends IBaseService<Invoice, InvoiceViewModel> {
    List<InvoiceDetailViewModel> test();
}
