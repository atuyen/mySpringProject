package com.seasolutions.stock_management.controller.security;

import com.seasolutions.stock_management.model.support.RequestData;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.security.authorization.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Component
public class CategoryAuthorization implements Authorization {
    @Autowired
    private RequestData requestData;

    public boolean findAll(ServletRequest request, ServletResponse response, SortOptions sortOptions,long employeeId){
       return  requestData.getTokenPayload().getEmployeeViewModel().getId()==employeeId;
   }



}
