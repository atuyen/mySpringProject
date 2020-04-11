package com.seasolutions.stock_management.model.support.filter;

import java.util.HashMap;
import java.util.Map;

public interface Filter {
    String genFilter();
    Map<String,Object> getParams();

}
