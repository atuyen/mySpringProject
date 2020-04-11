package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.filter.CompanyFilter;
import com.seasolutions.stock_management.model.view_model.CompanyViewModel;
import com.seasolutions.stock_management.service.interfaces.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    @Autowired
    ICompanyService companyService;


    @GetMapping
    List<CompanyViewModel> getCompanies(SortOptions sortOptions, CompanyFilter filter){
        return  companyService.findAll(sortOptions,filter);
    }


    @GetMapping
    @RequestMapping(path = "/test")
    List<CompanyViewModel> test(){
        return  companyService.test();
    }



}
