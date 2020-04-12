package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public class UnauthorizedRequestResponseWrapper extends BaseResponseWrapper<Void> {

    public UnauthorizedRequestResponseWrapper() {
        this.message="Unauthorized";
    }

    public UnauthorizedRequestResponseWrapper(String message) {
        this.message=message;
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



