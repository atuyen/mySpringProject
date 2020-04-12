package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionResponseWrapper  extends BaseResponseWrapper<List<String>> {
    private Throwable throwable;

    public ExceptionResponseWrapper(Throwable throwable){
        this.throwable=throwable;
    }


    @Override
    public ResponseStatuses getStatus() {
        return ResponseStatuses.FAILED;
    }

    @Override
    public String getMessage() {
        return  throwable!=null?throwable.getMessage():null;
    }

    @Override
    public List<String> getData() {
        if(throwable!=null){
            String[] stackFrames = ExceptionUtils.getStackFrames(throwable);
            return Arrays.asList(stackFrames);
        }
        return new ArrayList<>();
    }
}
