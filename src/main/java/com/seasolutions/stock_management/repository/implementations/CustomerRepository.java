package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Customer;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.ICustomerRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class CustomerRepository extends BaseRepository<Customer> implements ICustomerRepository {
}
