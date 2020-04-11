package com.seasolutions.stock_management.model.support.response_wrapper;

public class BadRequestResponseWrapper implements WrapperResponse<Object> {

    private final String message;
    private final Object data;

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
    public String getMessage() {
        return message;
    }

    @Override
    public String getStatus() {
        return "fail";
    }

    @Override
    public Object getData() {
        return data;
    }
}
