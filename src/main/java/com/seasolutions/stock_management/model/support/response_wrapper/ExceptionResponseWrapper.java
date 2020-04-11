package com.seasolutions.stock_management.model.support.response_wrapper;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class ExceptionResponseWrapper  implements WrapperResponse<String>{
    private Throwable throwable;


    public  ExceptionResponseWrapper(Throwable throwable){
        this.throwable=throwable;
    }

    @Override
    public String getMessage() {
        return throwable!=null?throwable.getMessage():null;
    }

    @Override
    public String getStatus() {
        return "failed";
    }

    @Override
    public String getData() {
        if(throwable!=null){
            String stackFrames =  ExceptionUtils.getStackTrace(throwable);
            return  stackFrames;
        }

        return null;
    }
}
