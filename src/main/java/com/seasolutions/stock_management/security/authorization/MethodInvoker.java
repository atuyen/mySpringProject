package com.seasolutions.stock_management.security.authorization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
    private final Method method;
    private final Object owner;

    public MethodInvoker(final Object owner, final Method method) {
        this.method = method;
        this.owner = owner;
    }

    public Object invoke(final Object...args) throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(owner, args);
    }
}
