package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Customer;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.ICustomerRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository extends BaseRepository<Customer> implements ICustomerRepository {
    @Override
    public List<Customer> test() {
        String query = "Select DISTINCT(e1) from Employee e1 "+
                "join Employee  e2 on e1.birthday = e2.transactionName ";

        return findDataByQuery(query,null);
    }
}
