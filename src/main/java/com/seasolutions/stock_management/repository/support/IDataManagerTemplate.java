package com.seasolutions.stock_management.repository.support;

import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface IDataManagerTemplate<T> {
    void setLogQuery(boolean log);

    List<T> findAll(Class<T> zClass, SortOptions sortOptions, EntityFilter filter);

    List<T> findAll(Class<T> zClass, PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter, AtomicLong totalRecord);

    List<T> findDataByQuery(String query, Map<String,Object> params);

    List<T> findDataByQuery(String query, Map<String,Object> params,int offset,int limit);

    T findById(Class<T> tClass, long id);

    T add(T t);

    T update(T t);

    void delete(Class<T> zClass,long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void delete(T t);

    void executeQuery(String query,Map<String,Object> params);
}
