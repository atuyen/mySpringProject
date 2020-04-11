package com.seasolutions.stock_management.model.support.response_wrapper;


public class DefaultWrapperResponse<E> implements WrapperResponse<E> {
    protected String message ;
    protected String status;
    protected E data;

    public DefaultWrapperResponse(E data){
        this.data = data;
    }


    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getStatus() {
        return "Success";
    }

    @Override
    public E getData() {
        return data;
    }
}
