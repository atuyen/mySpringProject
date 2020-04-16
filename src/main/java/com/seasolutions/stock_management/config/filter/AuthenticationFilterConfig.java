package com.seasolutions.stock_management.config.filter;

import com.seasolutions.stock_management.security.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationFilterConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean authenticationFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter(applicationContext));
        registration.addUrlPatterns("/*");
        registration.setName("authenticationFilter");
        registration.setOrder(FilterOrder.AUTHENTICATION_FILTER.ordinal());
        return registration;
    }

}
