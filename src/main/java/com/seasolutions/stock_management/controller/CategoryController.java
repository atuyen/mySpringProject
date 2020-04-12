package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultPaginatedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultResponseWrapper;
import com.seasolutions.stock_management.model.view_model.CategoryViewModel;
import com.seasolutions.stock_management.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;


    @GetMapping
    DefaultResponseWrapper<List<CategoryViewModel>> findAll(ServletRequest request, ServletResponse response, SortOptions sortOptions) {
        List<CategoryViewModel> data = categoryService.findAll(sortOptions, null);
        return new DefaultResponseWrapper<>(data);
    }

    @GetMapping
    @RequestMapping(path = "/paging")
    DefaultPaginatedResponseWrapper<CategoryViewModel> findAll(PaginationOptions paginationOptions, SortOptions sortOptions) {
        PaginatedResponse<CategoryViewModel> result = categoryService.findAll(paginationOptions, sortOptions, null);
        return new DefaultPaginatedResponseWrapper<CategoryViewModel>(result) {
        };
    }


    @RequestMapping(path = "/{id}")
    DefaultResponseWrapper<CategoryViewModel> findById(@PathVariable long id) {
        CategoryViewModel data = categoryService.findById(id);
        return  new DefaultResponseWrapper<>(data);
    }

    @PostMapping
    CategoryViewModel add(ServletRequest request, ServletResponse response,@RequestBody CategoryViewModel productViewModel) {
        return categoryService.add(productViewModel);
    }


    @PatchMapping
    CategoryViewModel update(@RequestBody CategoryViewModel productViewModel) {
        return categoryService.update(productViewModel);
    }


    @DeleteMapping(path = "/{id}")
    String delete(@PathVariable long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        categoryService.delete(id);
        return "Sucess";
    }


}
