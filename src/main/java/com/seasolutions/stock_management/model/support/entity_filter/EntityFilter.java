package com.seasolutions.stock_management.model.support.entity_filter;

import java.util.Map;

public interface EntityFilter {
    String genFilter();
    Map<String,Object> getParams();

}
