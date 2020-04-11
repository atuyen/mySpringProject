package com.seasolutions.stock_management.model.support.response_wrapper;

public class NotFoundResponseWrapper implements WrapperResponse<Void> {
    private String message = "Not found";

    public NotFoundResponseWrapper() { }

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
    public String getStatus() {
        return "fail";
    }

    @Override
    public Void getData() {
        return null;
    }
}
