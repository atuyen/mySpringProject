package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public interface ResponseWrapper<E> {
    String getMessage();
    ResponseStatuses getStatus();
    E getData();

}
