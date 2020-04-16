package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.PaginatedResponse;

import java.util.List;

public interface PagingResponseWrapper<E> extends ResponseWrapper<List<E>> {
    PaginatedResponse<E> getPaginatedResponse();
}
