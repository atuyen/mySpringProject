package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.CompanyEntityFilter;
import com.seasolutions.stock_management.model.view_model.CompanyViewModel;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
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
    public ICompanyService companyService;


    @GetMapping
    public List<CompanyViewModel> getCompanies(SortOptions sortOptions, CompanyEntityFilter filter) {
        return companyService.findAll(sortOptions, filter);
    }


    @Unauthenticated
    @GetMapping(path = "/test")
    public List<CompanyViewModel> test() {
        return companyService.test();
    }


}
