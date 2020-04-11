package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Category;
import com.seasolutions.stock_management.model.view_model.CategoryViewModel;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.ICategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional // level class
public class CategoryService extends BaseService<Category, CategoryViewModel> implements ICategoryService {

}
