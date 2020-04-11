package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.view_model.CustomerViewModel;
import com.seasolutions.stock_management.service.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping
    List<CustomerViewModel> test(){
        return  customerService.test();
    }


}
