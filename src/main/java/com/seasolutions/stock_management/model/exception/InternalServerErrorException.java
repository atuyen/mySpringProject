package com.seasolutions.stock_management.model.exception;

public class InternalServerErrorException extends RuntimeException {
    //Tra ra exception khi xay ra 1 loi nhu khong the ghi file...


    public InternalServerErrorException(final String msg) {
        super(msg);
    }

    public InternalServerErrorException(final String msg, final Exception e) {
        super(msg, e);
    }

}
