package com.seasolutions.stock_management.exception;
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(final String msg) {
        super(msg);
    }
}
