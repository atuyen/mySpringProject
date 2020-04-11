package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.InvoiceDetail;
import com.seasolutions.stock_management.model.view_model.InvoiceDetailViewModel;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.IInvoiceDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class InvoiceDetailService extends BaseService<InvoiceDetail, InvoiceDetailViewModel> implements IInvoiceDetailService {
}
