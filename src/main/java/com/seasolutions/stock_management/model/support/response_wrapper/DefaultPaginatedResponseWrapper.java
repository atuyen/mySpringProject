package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.PaginatedResponse;

import java.util.List;

public class DefaultPaginatedResponseWrapper<E> extends DefaultWrapperResponse<List<E>> implements PaginatedResponseWrapper<E> {
    private PaginatedResponse<E> paginatedResponse;

    @Override
    public PaginatedResponse<E> getPaginatedResponse() {
        return paginatedResponse;
    }

    public DefaultPaginatedResponseWrapper(PaginatedResponse<E> paginatedResponse) {
        super(paginatedResponse.getData());
        this.paginatedResponse = paginatedResponse;
    }
}
