package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.PaginatedResponse;

import java.util.List;

public interface PaginatedResponseWrapper<E> extends WrapperResponse<List<E>> {
    PaginatedResponse<E> getPaginatedResponse();
}
