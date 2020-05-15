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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

//@CrossOrigin(origins ={"*"},methods = {RequestMethod.POST,RequestMethod.GET})
@Authorized(CategoryAuthorization.class)
@RestController
public class CategoryController {

    @Autowired
    ICategoryService categoryService;



    @ApiOperation(value = "Tra ve tat ca category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Student not found"),
            @ApiResponse(code = 403, message = "Exception failed")
    })
    @Unauthenticated
    @GetMapping(path = "/categories")
    public ResponseWrapper<List<CategoryViewModel>> findAll( SortOptions sortOptions,
                                            @ApiParam(value = "id de xac thuc",required = true) @RequestParam long employeeId) {
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

    @Unauthenticated
    @GetMapping(path = "/test/{name}")
    public Person test(@PathVariable String name){
        return new Person(name);
    }
    @Unauthenticated
    @GetMapping(path = "/test5")
    public Person test(){
        return new Person("phuong");
    }

    @Unauthenticated
    @PostMapping(path = "/test3")
    public Person post(){
        return new Person("Tuy");
    }

    @Unauthenticated
    @PostMapping(path = "/test2")
    public Person post(@RequestBody Person person){
        return person;
    }


    //    @Unauthenticated
//    @PostMapping(path = "/categories")
    public CategoryViewModel add(ServletRequest request, ServletResponse response,@RequestBody CategoryViewModel productViewModel) {
        return categoryService.add(productViewModel);
    }
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

class Person{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }
    public  Person(){

    }

    private String name;
}

