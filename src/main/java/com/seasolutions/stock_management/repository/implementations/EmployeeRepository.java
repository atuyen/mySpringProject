package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IEmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class EmployeeRepository extends BaseRepository<Employee> implements IEmployeeRepository {

    @Override
    public Employee findByEmail(String email) {
        String query = "select e  from Employee e where e.email = :email ";
        Map<String,Object> params = new HashMap<>();
        params.put("email",email);
        List<Employee> employees = findDataByQuery(query,params);
        return  employees.size()>0?employees.get(0):null;
    }

}
