package com.seasolutions.stock_management.model.exception;

import java.util.ArrayList;
import java.util.List;

public class BadArgumentException extends RuntimeException {

    final List<Object> data;

    public BadArgumentException(final String msg) {
        super(msg);
        this.data = new ArrayList<>();
        this.data.add(msg);
    }

    public BadArgumentException(final String msg, final List<Object> data) {
        super(msg);
        this.data = data;
    }

    public BadArgumentException(final String msg, final Exception e, final List<Object> data) {
        super(msg, e);
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }
}
