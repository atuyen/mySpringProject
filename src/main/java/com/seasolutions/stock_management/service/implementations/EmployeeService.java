package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.repository.interfaces.IEmployeeRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.IEmployeeService;
import com.seasolutions.stock_management.util.MappingUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService extends BaseService<Employee, EmployeeViewModel> implements IEmployeeService {
    @Autowired
    IEmployeeRepository employeeRepository;

    @Override
    public List<EmployeeViewModel> test() {
        String query= String.join(" ", new String[] {
                "select e  from Employee e ",
                "where e.salary = (SELECT MAX(e.salary) FROM Employee e)"
        });
        List<Employee> employees = employeeRepository.findDataByQuery(query,null);
        List<EmployeeViewModel> employeeViewModels = MappingUtils.map(employees,new TypeToken<List<EmployeeViewModel>>(){}.getType());
        return employeeViewModels;
    }
}
