package com.seasolutions.stock_management.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerFilterConfig {

    @Bean
    public FilterRegistrationBean filterExceptionHandler() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ExceptionHandlerFilter());
        registration.addUrlPatterns("/*");
        registration.setName("filterExceptionHandler");
        registration.setOrder(FilterOrder.FILTER_EXCEPTION_HANDLER.ordinal());
        return registration;
    }

}
