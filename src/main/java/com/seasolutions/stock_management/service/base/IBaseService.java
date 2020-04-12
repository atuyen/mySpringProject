package com.seasolutions.stock_management.service.base;

import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IBaseService<T,V> {
    List<V> findAll(SortOptions sortOptions, EntityFilter filter);

    PaginatedResponse<V> findAll(PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter);

    V findById(long id);

    V add(V v);

    V update(V v);

    void delete(V v);

    void delete(long id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;


}
