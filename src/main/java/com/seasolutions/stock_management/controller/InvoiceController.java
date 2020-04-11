package com.seasolutions.stock_management.controller;

import com.seasolutions.stock_management.model.view_model.InvoiceViewModel;
import com.seasolutions.stock_management.service.interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/invoices")
public class InvoiceController {


    @Autowired
    IInvoiceService invoiceService;

    @GetMapping
    @RequestMapping("/{id}")
    public InvoiceViewModel findById(@PathVariable long id){
        return invoiceService.findById(id);
    }


    @GetMapping
    public List<InvoiceViewModel> test(){
        return  invoiceService.findAll(null,null);
    }



}
