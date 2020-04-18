package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.controller.security.CategoryAuthorization;
import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultPaginatedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.PagingResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.ResponseWrapper;
import com.seasolutions.stock_management.model.view_model.CategoryViewModel;
import com.seasolutions.stock_management.security.annotation.Authorized;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import com.seasolutions.stock_management.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


@Authorized(CategoryAuthorization.class)
@RestController
public class CategoryController {

    @Autowired
    ICategoryService categoryService;



    @GetMapping(path = "/categories")
    public ResponseWrapper<List<CategoryViewModel>> findAll(ServletRequest request, ServletResponse response, SortOptions sortOptions,
                                                           @RequestParam long employeeId) {
        List<CategoryViewModel> data = categoryService.findAll(sortOptions, null);
        return new DefaultResponseWrapper<>(data);
    }

    @Unauthenticated
    @GetMapping(path = "/categories/paging")
    public   PagingResponseWrapper<CategoryViewModel> findAll(PaginationOptions paginationOptions, SortOptions sortOptions) {
        PaginatedResponse<CategoryViewModel> result = categoryService.findAll(paginationOptions, sortOptions, null);
        return new DefaultPaginatedResponseWrapper<CategoryViewModel>(result) {
        };
    }


    @Unauthenticated
    @GetMapping(path = "/categories/{id}")
    public  DefaultResponseWrapper<CategoryViewModel> findById(@PathVariable long id) {
        CategoryViewModel data = categoryService.findById(id);
        return  new DefaultResponseWrapper<>(data);
    }
//
//    @Unauthenticated
//    @PostMapping(path = "/categories")
//    public CategoryViewModel add(ServletRequest request, ServletResponse response,@RequestBody CategoryViewModel productViewModel) {
//        return categoryService.add(productViewModel);
//    }
//
//
//    @Unauthenticated
//    @PatchMapping(path = "/categories")
//    public CategoryViewModel update(@RequestBody CategoryViewModel productViewModel) {
//        return categoryService.update(productViewModel);
//    }
//
//    @Unauthenticated
//    @DeleteMapping(path = "/categories/{id}")
//    public String delete(@PathVariable long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        categoryService.delete(id);
//        return "Sucess";
//    }


}
