package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Company;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.ICompanyRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository
public class CompanyRepository extends BaseRepository<Company> implements ICompanyRepository {
}
