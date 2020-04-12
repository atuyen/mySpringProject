package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public class BadRequestResponseWrapper extends BaseResponseWrapper<Object> {


    public BadRequestResponseWrapper(final Object data, final String message) {
        this.data = data;
        this.message = message;
    }

    public BadRequestResponseWrapper(final Object data) {
        this.data = data;
        this.message = "Bad request";
    }

    public BadRequestResponseWrapper() {
        this.data = null;
        this.message = "Bad request";
    }

    @Override
    public ResponseStatuses getStatus() {
        return ResponseStatuses.FAILED;
    }

}
