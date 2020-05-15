package com.seasolutions.stock_management.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component("httpClientPerformanceInterceptor")
public class HttpClientPerformanceInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
//        final PerformanceMonitor performanceMonitor = PerformanceMonitor.getInstance();
        final long startTime = System.currentTimeMillis();
        final String method = request.getMethod().name();
        final String uri = request.getURI().toString();
        log.debug("Calling HTTP : {} {}", method, uri);
//        performanceMonitor.start("http");
        try {
            return execution.execute(request, body);
        } finally {
//            performanceMonitor.stop("http");
            final long endTime = System.currentTimeMillis();
            log.info("Invoked HTTP [{}ms]: {} {}", (endTime - startTime), method, uri);
        }
    }
}
