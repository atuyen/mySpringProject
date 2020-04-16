package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Company;
import com.seasolutions.stock_management.model.view_model.CompanyViewModel;
import com.seasolutions.stock_management.repository.interfaces.ICompanyRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.ICompanyService;
import com.seasolutions.stock_management.util.MappingUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



@Transactional
@Service
public class CompanyService extends BaseService<Company, CompanyViewModel> implements ICompanyService {
    @Autowired
    ICompanyRepository companyRepository;

    @Override
    public List<CompanyViewModel> test() {
        List<Company> companies = companyRepository.test();
        return MappingUtils.map(companies, new TypeToken<List<CompanyViewModel>>(){}.getType());
    }
}
