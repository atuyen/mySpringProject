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
        String query = "select co "+
                "from Company co  join Product p on co.id = p.company.id "+
                " join Category ca on p.category.id = ca.id " +
                "where ca.name = 'Tu Lanh' ";






        List<Company> companies = companyRepository.findDataByQuery(query,null);
        return MappingUtils.map(companies, new TypeToken<List<CompanyViewModel>>(){}.getType());
    }
}
