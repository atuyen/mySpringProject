package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.Company;
import com.seasolutions.stock_management.model.view_model.CompanyViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;

import java.util.List;

public interface ICompanyService extends IBaseService<Company, CompanyViewModel> {
    List<CompanyViewModel> test();
}
