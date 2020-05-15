package com.seasolutions.stock_management.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import org.apache.http.client.HttpClient;
import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private HttpClientPerformanceInterceptor interceptor;

    @Bean
    public RestTemplate configureRestClient() {
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(27000);
        factory.setConnectTimeout(7000);
        final RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }

}