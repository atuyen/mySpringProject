package com.seasolutions.stock_management.repository.support;

import com.seasolutions.stock_management.model.entity.BaseModel;
import com.seasolutions.stock_management.model.support.PaginationOptions;
import com.seasolutions.stock_management.model.support.SortOptions;
import com.seasolutions.stock_management.model.support.entity_filter.EntityFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;

@Log4j2
@Transactional
@Component
public class DataManagerTemplate<T extends BaseModel> implements IDataManagerTemplate<T> {
    private static boolean hasLogQueries = true;

    @Override
    public void setLogQuery(boolean log) {
        hasLogQueries = log;
    }

    @PersistenceContext
    private EntityManager entityManager;




    @Override
    public List<T> findAll(Class<T> zClass, SortOptions sortOptions, EntityFilter filter) {
        String strQuery = String.format("Select a From  %s AS a %s", zClass.getSimpleName(), buildConditionQuery(filter));

        logQuery("findAll ", strQuery, filter);

        Query jpaQuery = entityManager.createQuery(strQuery);
        setParametersFromFilter(jpaQuery, filter);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> findDataByQuery(String query, Map<String, Object> params) {
        logQuery("findAll ", query, params);

        Query jpaQuery = entityManager.createQuery(query);
        setParamsForQuery(jpaQuery, params);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> findDataByQuery(String query, Map<String, Object> params, int offset, int limit) {
        logQuery("findAll ", query, params);
        Query jpaQuery = entityManager.createQuery(query);
        setParamsForQuery(jpaQuery, params);
        jpaQuery.setFirstResult(offset);
        jpaQuery.setMaxResults(limit);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> findAll(Class<T> zClass, PaginationOptions paginationOptions, SortOptions sortOptions, EntityFilter filter, AtomicLong totalRecord) {
        String strQuery = String.format("Select a From %s AS a %s %s ", zClass.getSimpleName(), buildConditionQuery(filter), buildOrderQuery(sortOptions));
        String strCountQuery = String.format("Select count (*)  from  %s AS a %s", zClass.getSimpleName(), buildConditionQuery(filter));

        logQuery("findAll ", strQuery, filter);
        logQuery("count ", strCountQuery, filter);

        Query jpaQuery = entityManager.createQuery(strQuery);
        Query jpaCountQuery = entityManager.createQuery(strCountQuery);
        jpaQuery.setFirstResult(paginationOptions.getOffset());
        jpaQuery.setMaxResults(paginationOptions.getPagSize());
        setParametersFromFilter(jpaQuery, filter);
        setParametersFromFilter(jpaCountQuery, filter);
        List<T> data = jpaQuery.getResultList();
        totalRecord.set((long) jpaCountQuery.getSingleResult());
        return data;
    }

    @Override
    public T findById(Class<T> tClass, long id) {
        log.info("Find by id " + tClass.getSimpleName() + " " + id);
        log.info("\n\n");

        return entityManager.find(tClass, id);
    }

    @Override
    public T add(T t) {
        t.setId(null);

        log.info("Persist 1 entity " + t.getClass().getSimpleName());
        log.info(t.toString());
        log.info("\n\n");

        entityManager.persist(t);
        return t;
    }

    @Override
    public T update(T t) {

        log.info("Update 1 entity " + t.getClass().getSimpleName());
        log.info(t.toString());
        log.info("\n\n");

        entityManager.merge(t);
        return t;
    }

    @Override
    public void delete(Class<T> zClass, long id) {
        String query = String.format("DELETE FROM %s e WHERE e.id = %s", zClass.getSimpleName(), id);
        delete(query, zClass.getSimpleName());
    }

    @Override
    public void delete(T t) {
        String query = String.format("DELETE FROM %s e WHERE e.id = %s", t.getClass().getSimpleName(), t.getId());
        delete(query, t.getClass().getSimpleName());
    }

    @Override
    public void executeQuery(String query, Map<String, Object> params) {
        logQuery("execute query ", query, params);
        Query jpaQuery = entityManager.createQuery(query);
        setParamsForQuery(jpaQuery, params);
        jpaQuery.executeUpdate();
    }

    private void delete(String query, String className) {
        log.info("Delete 1 entity ".concat(className));
        log.info(query);
        log.info("\n\n");

        entityManager.createQuery(query).executeUpdate();
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

    private String buildConditionQuery(EntityFilter filter) {
//        return "where a.activeFlag = true";
        if (filter == null || filter.genFilter().isEmpty()) {
            return " ";
        } else {
            return String.format(" where %s", filter.genFilter());
        }
    }

    private void setParametersFromFilter(Query query, EntityFilter filter) {
        if (filter != null) {
            setParamsForQuery(query, filter.getParams());
        }
    }

    private void setParamsForQuery(Query query, Map<String, Object> params) {
        if (params != null) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }
    }


    private void logQuery(final String prefix, final String query, final EntityFilter filter) {
        Map<String, Object> params = filter != null ? filter.getParams() : null;
        logQuery(prefix, query, params);
    }

    private void logQuery(final String prefix, final String query, final Map<String, Object> params) {
        if (hasLogQueries) {
            String expandedQuery = query;
            if (params != null && !params.isEmpty()) {
                for (final Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        expandedQuery = expandedQuery.replaceAll(":" + entry.getKey(), Matcher.quoteReplacement(entry.getValue().toString()));
                    }
                }
            }
            log.info(String.format("%s: %s", prefix, expandedQuery));
            log.info("\n\n");
        }
    }


}
