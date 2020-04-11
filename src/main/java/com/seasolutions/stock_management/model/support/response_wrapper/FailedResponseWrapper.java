package com.seasolutions.stock_management.model.support.response_wrapper;

public class FailedResponseWrapper implements WrapperResponse<Void> {

    private final String message;

    public FailedResponseWrapper(final String message) {
        this.message = message;
    }

    public FailedResponseWrapper() {
        this.message = "failed";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getStatus() {
        return "fail";
    }

    @Override
    public Void getData() {
        return null;
    }
}
