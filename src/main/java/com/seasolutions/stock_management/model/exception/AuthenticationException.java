package com.seasolutions.stock_management.model.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(final String msg) {
        super(msg);
    }

    public AuthenticationException(final String msg, final Throwable e) {
        super(msg, e);
    }

}
