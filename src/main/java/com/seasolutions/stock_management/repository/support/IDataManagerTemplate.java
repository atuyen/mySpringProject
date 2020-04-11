package com.seasolutions.stock_management.repository.support;

import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.filter.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface IDataManagerTemplate<T> {
    List<T> findAll(Class<T> zClass, SortOptions sortOptions, Filter filter);

    List<T> findAll(Class<T> zClass, PaginationOptions paginationOptions, SortOptions sortOptions,Filter filter, AtomicLong totalRecord);

    List<T> findDataByQuery(String query, Map<String,Object> params);

    T findById(Class<T> tClass, long id);

    T add(T t);

    T update(T t);

    void delete(Class<T> zClass,long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void delete(T t);
}
