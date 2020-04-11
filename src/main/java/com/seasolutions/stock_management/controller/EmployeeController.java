package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;

    @GetMapping
    List<EmployeeViewModel> getEmployees(SortOptions sortOptions){
        return  employeeService.findAll(sortOptions,null);
    }


}
