package com.seasolutions.stock_management.security.annotation;


import com.seasolutions.stock_management.security.authorization.Authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Indicates that a controller method requires authorization by the given {@link Authorization} class.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {
    Class<? extends Authorization> value();
}
