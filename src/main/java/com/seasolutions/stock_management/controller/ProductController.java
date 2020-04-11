package com.seasolutions.stock_management.controller;

import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.filter.ProductFilter;
import com.seasolutions.stock_management.model.view_model.ProductViewModel;
import com.seasolutions.stock_management.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


@RestController
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    IProductService productService;


    @GetMapping
    List<ProductViewModel> findAll(SortOptions sortOptions, ProductFilter filter){
        return  productService.findAll(sortOptions,filter);
    }

    @GetMapping
    @RequestMapping(path = "/paging")
    PaginatedResponse<ProductViewModel> findAll(PaginationOptions paginationOptions, SortOptions sortOptions,ProductFilter filter){
        return  productService.findAll(paginationOptions,sortOptions,filter);
    }


    @RequestMapping(path = "/{id}")
    ProductViewModel findById(@PathVariable long id){
        return  productService.findById(id);
    }

    @PostMapping
    ProductViewModel add(@RequestBody ProductViewModel productViewModel){
        return  productService.add(productViewModel);
    }


    @PatchMapping
    ProductViewModel update(@RequestBody ProductViewModel productViewModel){
        return  productService.update(productViewModel);
    }


    @DeleteMapping(path = "/{id}")
    String delete(@PathVariable long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        productService.delete(id);
        return  "Sucess";
    }







}
