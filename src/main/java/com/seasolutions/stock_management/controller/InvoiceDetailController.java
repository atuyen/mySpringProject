package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.InvoiceDetailEntityFilter;
import com.seasolutions.stock_management.model.view_model.InvoiceDetailViewModel;
import com.seasolutions.stock_management.service.interfaces.IInvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/invoice-details")
public class InvoiceDetailController {

    @Autowired
    IInvoiceDetailService invoiceDetailService;



    @RequestMapping
    List<InvoiceDetailViewModel> test(SortOptions sortOptions, InvoiceDetailEntityFilter filter){
        return  invoiceDetailService.findAll(sortOptions,filter);

    }





}
