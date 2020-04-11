package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Customer;
import com.seasolutions.stock_management.model.view_model.CustomerViewModel;
import com.seasolutions.stock_management.repository.interfaces.ICustomerRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.ICustomerService;
import com.seasolutions.stock_management.util.MappingUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CustomerService extends BaseService<Customer, CustomerViewModel> implements ICustomerService {
    @Autowired
    ICustomerRepository customerRepository;

    @Override
    public List<CustomerViewModel> test() {

        String query = "Select DISTINCT(e1) from Employee e1 "+
                "join Employee  e2 on e1.birthday = e2.transactionName ";


        List<Customer> customers =  customerRepository.findDataByQuery(query,null);
        return MappingUtils.map(customers,new TypeToken<List<CustomerViewModel>>(){}.getType());
    }
}
