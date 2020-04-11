package com.seasolutions.stock_management.repository.support;

import com.seasolutions.stock_management.model.entity.BaseModel;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.filter.Filter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Transactional
@Component
public class DataManagerTemplate<T extends BaseModel> implements IDataManagerTemplate<T> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<T> findAll(Class<T> zClass, SortOptions sortOptions, Filter filter) {
        String strQuery = String.format("Select a From  %s AS a %s", zClass.getSimpleName(), buildConditionQuery(filter));
        Query jpaQuery = entityManager.createQuery(strQuery);
        setParametersFromFilter(jpaQuery,filter);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> findDataByQuery(String query, Map<String, Object> params) {
        Query jpaQuery = entityManager.createQuery(query);
        setParamsForQuery(jpaQuery,params);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> findAll(Class<T> zClass, PaginationOptions paginationOptions, SortOptions sortOptions,Filter filter, AtomicLong totalRecord) {
        String strQuery = String.format("Select a From %s AS a %s %s ", zClass.getSimpleName(), buildConditionQuery(filter), buildOrderQuery(sortOptions));
        String strCountQuery = String.format("Select count (*)  from  %s AS a %s", zClass.getSimpleName(), buildConditionQuery(filter));

        Query jpaQuery = entityManager.createQuery(strQuery);
        Query jpaCountQuery = entityManager.createQuery(strCountQuery);
        jpaQuery.setFirstResult(paginationOptions.getOffset());
        jpaQuery.setMaxResults(paginationOptions.getPagSize());
        setParametersFromFilter(jpaQuery,filter);
        setParametersFromFilter(jpaCountQuery,filter);
        List<T> data = jpaQuery.getResultList();
        totalRecord.set((long) jpaCountQuery.getSingleResult());
        return data;
    }

    @Override
    public T findById(Class<T> tClass, long id) {
        return entityManager.find(tClass, id);
    }

    @Override
    public T add(T t) {
        t.setId(null);
        entityManager.persist(t);
        return t;
    }

    @Override
    public T update(T t) {
        entityManager.merge(t);
        return t;
    }

    @Override
    public void delete(Class<T> zClass, long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Query query = entityManager.createQuery(String.format("DELETE FROM %s e WHERE e.id = %s", zClass.getSimpleName(), id));
        query.executeUpdate();
    }

    @Override
    public void delete(T t) {
        Query query = entityManager.createQuery(String.format("DELETE FROM %s e WHERE e.id = %s", t.getClass().getSimpleName(), t.getId()));
        query.executeUpdate();
    }


    private String buildOrderQuery(SortOptions sortOptions) {
        if (sortOptions == null || sortOptions.getSorts().size() == 0)
            return "";
        List<String> sorts = sortOptions.getSorts();
        List<String> orders = sortOptions.getOrders();
        StringBuilder orderBuilder = new StringBuilder();
        for (int i = 0; i < sorts.size(); i++) {
            orderBuilder.append(sorts.get(i));
            orderBuilder.append(" " + orders.get(i));
            if (i < sorts.size() - 1) {
                orderBuilder.append(",");
            }
        }
        String orderQuery = String.format(" Order by %s", orderBuilder.toString());
        return orderQuery;
    }

    private String buildConditionQuery(Filter filter) {
//        return "where a.activeFlag = true";
        if(filter==null || filter.genFilter().isEmpty()){
            return " ";
        }else {
            return String.format(" where %s",filter.genFilter());
        }
    }

    private void setParametersFromFilter(Query query,Filter filter){
       if(filter!=null){
           setParamsForQuery(query,filter.getParams());
       }
    }
    private void setParamsForQuery(Query query,Map<String,Object> params){
        if(params!=null){
            params.forEach((k,v)->{
                query.setParameter(k,v);
            });
        }
    }


}
