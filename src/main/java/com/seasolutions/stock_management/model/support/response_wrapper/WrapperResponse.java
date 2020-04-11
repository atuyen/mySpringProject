package com.seasolutions.stock_management.model.support.response_wrapper;

public interface WrapperResponse<E> {
    String getMessage();
    String getStatus();
    E getData();

}
