package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.Customer;
import com.seasolutions.stock_management.model.view_model.CustomerViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer, CustomerViewModel> {
    List<CustomerViewModel> test();

}
