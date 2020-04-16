package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.security.RegisterInfo;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultResponseWrapper;
import com.seasolutions.stock_management.service.interfaces.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/registers")
public class RegisterController {

    @Autowired
    IRegisterService registerService;


    @PostMapping
    DefaultResponseWrapper<String> register(@RequestBody RegisterInfo registerInfo){
        registerService.register(registerInfo);
        return  new DefaultResponseWrapper<>("success");
    }




}
