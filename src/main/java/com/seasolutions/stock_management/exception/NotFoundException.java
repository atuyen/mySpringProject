package com.seasolutions.stock_management.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String msg) {
        super(msg);
    }
    public NotFoundException(final String msg, final Exception e) {
        super(msg, e);
    }

}
