package com.seasolutions.stock_management.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class LoggingFilterConfig {

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoggingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("loggingFilter");
        registration.setOrder(FilterOrder.LOGGING_FILTER.ordinal());
        return registration;
    }

}
