package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public class UnAuthenticatedResponseWrapper extends BaseResponseWrapper<Void> {

    public UnAuthenticatedResponseWrapper() {
        this.message="UnAuthenticated";
    }

    public UnAuthenticatedResponseWrapper(String message) {
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
