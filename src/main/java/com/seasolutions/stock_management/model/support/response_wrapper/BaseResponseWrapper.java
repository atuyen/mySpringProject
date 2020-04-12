package com.seasolutions.stock_management.model.support.response_wrapper;


import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public abstract class BaseResponseWrapper<E>  {


    protected String message;
    protected ResponseStatuses status;
    protected E data;

    protected BaseResponseWrapper(String message,ResponseStatuses status,E data){
        this.message=message;
        this.status=status;
        this.data=data;
    }

    protected BaseResponseWrapper(E data){
        this.data=data;
    }

    protected BaseResponseWrapper(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatuses getStatus() {
        return ResponseStatuses.SUCCESS;
    }

    public void setStatus(ResponseStatuses status) {
        this.status = status;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}



