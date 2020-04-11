package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.entity.Product;
import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.view_model.ProductViewModel;
import com.seasolutions.stock_management.service.base.IBaseService;

import java.util.List;

public interface IProductService extends IBaseService<Product,ProductViewModel> {
    List<ProductViewModel> test();


}
