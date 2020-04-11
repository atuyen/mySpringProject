package com.seasolutions.stock_management.model.support.filter;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class CompanyFilter implements Filter {
    private String transactionName;
    private Map<String,Object> params = new HashMap<>();


    @Override
    public String genFilter() {

        List<String> conditions = new ArrayList<>();

        if (transactionName != null && !transactionName.isEmpty()) {
            conditions.add("a.transactionName = :transactionName");
            params.put("transactionName",transactionName);
        }




        String condition = String.join(" AND ", conditions);
        return condition;
    }


    @Override
    public Map<String, Object> getParams() {
        return params;
    }
}
