package com.seasolutions.stock_management.model.support.response_wrapper;

import com.seasolutions.stock_management.model.support.enumerable.ResponseStatuses;

public class FailedResponseWrapper extends BaseResponseWrapper<Void> {

    public FailedResponseWrapper(final String message) {
        this.message = message;
    }

    public FailedResponseWrapper() {
        super();
        this.message = "Failed";
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
