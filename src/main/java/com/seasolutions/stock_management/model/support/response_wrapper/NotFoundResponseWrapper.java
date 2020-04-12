package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public class NotFoundResponseWrapper extends BaseResponseWrapper<Void> {

    public NotFoundResponseWrapper() {
        this.message="Not found";
    }

    public NotFoundResponseWrapper(String message) {
        if (message != null) {
            this.message = "Not found (" + message + ")";
        }
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ResponseStatuses getStatus() {
        return ResponseStatuses.FAILED;
    }

    @Override
    public Void getData() {
        return null;
    }
}
