package com.seasolutions.stock_management.model.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(final String msg) {
        super(msg);
    }

    public AuthorizationException(final String msg, final Throwable t) {
        super(msg, t);
    }

}
