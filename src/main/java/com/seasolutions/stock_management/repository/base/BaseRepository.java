package com.seasolutions.stock_management.repository.base;

import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;
import com.seasolutions.stock_management.repository.support.IDataManagerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@Transactional
public class BaseRepository<T> implements IBaseRepository<T> {
    Class<T> zClass;

    public BaseRepository() {
        zClass = getGenericType();
    }


    @Autowired
    protected IDataManagerTemplate<T> dataManagerTemplate;

    @Override
    public void executeQuery(String query, Map<String, Object> params) {
        dataManagerTemplate.executeQuery(query,params);
    }

    @Override
    public List<T> findAll(SortOptions sortOptions, EntityFilter filter) {
        return dataManagerTemplate.findAll(zClass, sortOptions,filter);
    }

    @Override
    public T findById(long id) {
        return (T)dataManagerTemplate.findById(zClass,id);
    }

    @Override
    public List<T> findAll(PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter, AtomicLong totalRecord) {
        return dataManagerTemplate.findAll(zClass, paginationOptions, sortOptions,filter, totalRecord);
    }

    @Override
    public List<T> findDataByQuery(String query, Map<String, Object> params) {
        return  dataManagerTemplate.findDataByQuery(query,params);
    }

    @Override
    public List<T> findDataByQuery(String query, Map<String, Object> params, int offset, int limit) {
        return dataManagerTemplate.findDataByQuery(query,params,offset,limit);
    }

    @Override
    public T add(T t) {
        return dataManagerTemplate.add(t);
    }

    @Override
    public T update(T t) {
        return  dataManagerTemplate.update(t);
    }

    @Override
    public void delete(long id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        dataManagerTemplate.delete(zClass,id);
    }

    @Override
    public void delete(T t) {
        dataManagerTemplate.delete(t);
    }


    @Override
    public List<T> test() {
        return null;
    }

    private Class<T> getGenericType() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            return (Class<T>) pt.getActualTypeArguments()[0];
        } else {
            return null;
        }
    }




}
