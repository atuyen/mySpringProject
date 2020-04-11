package com.seasolutions.stock_management.repository.base;


import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.filter.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface IBaseRepository<T> {
    List<T> findAll(SortOptions sortOptions, Filter filter);

    List<T> findAll(PaginationOptions paginationOptions, SortOptions sortOptions, Filter filter, AtomicLong totalRecord);

    List<T> findDataByQuery(String query, Map<String,Object> params);


    T findById(long id);

    T add(T t);

    T update(T t);

    void delete(T t);

    void delete(long id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
