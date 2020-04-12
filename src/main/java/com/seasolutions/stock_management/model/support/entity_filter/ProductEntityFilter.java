package com.seasolutions.stock_management.model.support.entity_filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class ProductEntityFilter implements EntityFilter {
    private BigDecimal minCost;
    private Integer maxNumber;
    private String companyName;
    private Map<String, Object> params = new HashMap<>();

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String genFilter() {

        List<String> conditions = new ArrayList<>();

        if (minCost != null) {
            conditions.add(" a.cost >= :minCost ");
            params.put("minCost",minCost);
        }
        if (maxNumber != null) {
            conditions.add(" a.number <= :maxNumber ");
            params.put("maxNumber",maxNumber);
        }

        if (companyName != null) {
            conditions.add(" a.company.name = :companyName ");
            params.put("companyName",companyName);
        }


        String condition = String.join(" AND ", conditions);
        return condition;
    }
}
