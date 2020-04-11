package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;

import java.util.List;
import java.util.Map;

public interface IEmployeeService extends IBaseService<Employee, EmployeeViewModel> {
    List<EmployeeViewModel> test();

}
