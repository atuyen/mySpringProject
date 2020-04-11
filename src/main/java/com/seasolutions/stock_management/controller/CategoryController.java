package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.exception.NotFoundException;
import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultPaginatedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultWrapperResponse;
import com.seasolutions.stock_management.model.support.response_wrapper.PaginatedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.WrapperResponse;
import com.seasolutions.stock_management.model.view_model.CategoryViewModel;
import com.seasolutions.stock_management.service.interfaces.ICategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;


    @GetMapping
    WrapperResponse<List<CategoryViewModel>> findAll(SortOptions sortOptions) {
        List<CategoryViewModel> data = categoryService.findAll(sortOptions, null);
        return new DefaultWrapperResponse<>(data);
    }

    @GetMapping
    @RequestMapping(path = "/paging")
    PaginatedResponseWrapper<CategoryViewModel> findAll(PaginationOptions paginationOptions, SortOptions sortOptions) {
        PaginatedResponse<CategoryViewModel> result = categoryService.findAll(paginationOptions, sortOptions, null);
        return new DefaultPaginatedResponseWrapper<CategoryViewModel>(result) {
        };
    }


    @RequestMapping(path = "/{id}")
    WrapperResponse<CategoryViewModel> findById(@PathVariable long id) {
        CategoryViewModel data = categoryService.findById(id);
        return  new DefaultWrapperResponse<>(data);
    }

    @PostMapping
    CategoryViewModel add(@RequestBody CategoryViewModel productViewModel) {
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
