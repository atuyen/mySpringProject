package com.seasolutions.stock_management.model.support.response_wrapper;


import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;



public class DefaultResponseWrapper<E>  extends BaseResponseWrapper<E> {


    public DefaultResponseWrapper(E data){
        super(data);
        message=null;
        status= ResponseStatuses.SUCCESS;
    }

    public DefaultResponseWrapper(String message, ResponseStatuses status, E data){
       super(message,status,data);
    }

}
