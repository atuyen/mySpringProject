package com.seasolutions.stock_management.repository.interfaces;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.repository.base.IBaseRepository;

public interface IEmployeeRepository extends IBaseRepository<Employee> {
    Employee findByEmail(String email);
}
