package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.Company;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.ICompanyRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public class CompanyRepository extends BaseRepository<Company> implements ICompanyRepository {
    @Override
    public List<Company> test() {
        String query = "select co "+
                "from Company co  join Product p on co.id = p.company.id "+
                " join Category ca on p.category.id = ca.id " +
                "where ca.name = 'Tu Lanh' ";
        return findDataByQuery(query,null);
    }
}
