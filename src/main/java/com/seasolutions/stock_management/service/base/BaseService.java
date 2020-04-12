package com.seasolutions.stock_management.service.base;

import com.seasolutions.stock_management.model.entity.BaseModel;
import com.seasolutions.stock_management.model.exception.BadArgumentException;
import com.seasolutions.stock_management.model.exception.NotFoundException;
import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;
import com.seasolutions.stock_management.model.view_model.BaseViewModel;
import com.seasolutions.stock_management.repository.base.IBaseRepository;
import com.seasolutions.stock_management.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class BaseService<T,V extends BaseViewModel> implements IBaseService<T,V> {

    private Class<T> tClass;
    private Class<V> vClass;

   public BaseService(){
       setGenericType();
    }

    @Autowired
    IBaseRepository<T> baseRepository;

    @Override
    public List<V> findAll(SortOptions sortOptions, EntityFilter filter) {
        List<T> models = baseRepository.findAll(sortOptions,filter);
        List<V> viewModels = convertModelsToViewModels(models);
        return  viewModels;
    }

    @Override
    public PaginatedResponse<V> findAll(PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter) {
        AtomicLong totalRecord = new AtomicLong();
        List<T> models = baseRepository.findAll(paginationOptions,sortOptions,filter,totalRecord);
        List<V> viewModels =convertModelsToViewModels(models);
        return new PaginatedResponse(paginationOptions,totalRecord.get(),viewModels);
    }

    @Override
    public V findById(long id) {
        T t = baseRepository.findById(id);
        if(t==null){
           throw new NotFoundException("No resource found ("+tClass.getSimpleName()+" "+id +")");
        }
        V v = MappingUtils.map(t,vClass);
        return v;
    }

    @Override
    public V add(V v) {
        T t = MappingUtils.map(v,tClass);
        t = baseRepository.add(t);
        return MappingUtils.map(t,vClass);
    }

    @Override
    public V update(V v) {
        if(v.getId()==0){
            throw new BadArgumentException("Id must not be null");
        }
        T t = MappingUtils.map(v,tClass);
        t = baseRepository.update(t);
        return MappingUtils.map(t,vClass);
    }

    @Override
    public void delete(V v) {
        T t = MappingUtils.map(v,tClass);
        baseRepository.delete(t);
    }

    @Override
    public void delete(long id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        baseRepository.delete(id);
    }

    private void setGenericType() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            tClass = (Class<T>) pt.getActualTypeArguments()[0];
            vClass = (Class<V>) pt.getActualTypeArguments()[1];
        }
    }

    private List<V> convertModelsToViewModels(List<T> models){
     return    models.stream().map(new Function<T, V>() {
            @Override
            public V apply(T t) {
                return MappingUtils.map(t,vClass);
            }
        }).collect(Collectors.toList());
    }


}
