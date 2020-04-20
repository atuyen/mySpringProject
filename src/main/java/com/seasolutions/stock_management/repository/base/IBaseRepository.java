package com.seasolutions.stock_management.repository.base;


import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface IBaseRepository<T> {
    List<T> findAll(SortOptions sortOptions, EntityFilter filter);

    List<T> findAll(PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter, AtomicLong totalRecord);

    List<T> findDataByQuery(String query, Map<String,Object> params);
    List<T> findDataByQuery(String query, Map<String,Object> params,int offset,int limit);

    void executeQuery(String query,Map<String,Object> params);

    T findById(long id);

    T add(T t);

    T update(T t);

    void delete(T t);

    void delete(long id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    List<T> test();

}
