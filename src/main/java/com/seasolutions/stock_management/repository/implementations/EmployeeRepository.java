package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IEmployeeRepository;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeRepository extends BaseRepository<Employee> implements IEmployeeRepository {
}
