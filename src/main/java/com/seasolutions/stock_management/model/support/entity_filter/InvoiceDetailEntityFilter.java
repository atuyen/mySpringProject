package com.seasolutions.stock_management.model.support.entity_filter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class InvoiceDetailEntityFilter implements EntityFilter {

    private Map<String, Object> params = new HashMap<>();
    private Long invoiceId;



    @Override
    public String genFilter() {
        List<String> conditions = new ArrayList<>();

        if (invoiceId != null) {
            conditions.add(" a.invoice.id = :invoiceId ");
            params.put("invoiceId",invoiceId);
        }

        String condition = String.join(" AND ", conditions);
        return condition;
    }



    @Override
    public Map<String, Object> getParams() {
        return params;
    }
}
