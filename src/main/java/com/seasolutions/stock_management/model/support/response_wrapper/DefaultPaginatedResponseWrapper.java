package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.PaginatedResponse;
import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

import java.util.List;


public class DefaultPaginatedResponseWrapper<E> extends DefaultResponseWrapper<List<E>>  implements PagingResponseWrapper<E>{
    private PaginatedResponse<E> paginatedResponse;



    public DefaultPaginatedResponseWrapper(PaginatedResponse<E> paginatedResponse) {
        super(paginatedResponse.getData());
        this.paginatedResponse = paginatedResponse;
    }

    public DefaultPaginatedResponseWrapper(String message, ResponseStatuses status, PaginatedResponse<E> paginatedResponse){
        super(message,status,paginatedResponse.getData());
        this.paginatedResponse=paginatedResponse;
    }

    @Override
    public PaginatedResponse<E> getPaginatedResponse() {
        return paginatedResponse;
    }

    public void setPaginatedResponse(PaginatedResponse<E> paginatedResponse) {
        this.paginatedResponse = paginatedResponse;
    }
}
