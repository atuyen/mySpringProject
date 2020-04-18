package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.response_wrapper.DefaultResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.ResponseWrapper;
import com.seasolutions.stock_management.model.view_model.CategoryViewModel;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import com.seasolutions.stock_management.validation.ananotation.ProductIDExisting;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


//https://hellokoding.com/spring-boot-rest-api-validation-tutorial-with-example/

@RestController
@Validated
public class ValidationController {



    @Unauthenticated
    @GetMapping(path = "/validate")
    public   ResponseWrapper<String> validate(@RequestParam @ProductIDExisting long id){

        return new DefaultResponseWrapper<>("Success");
    }



}
