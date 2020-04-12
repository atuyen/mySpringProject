package com.seasolutions.stock_management.config.filter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seasolutions.stock_management.model.support.servlet_filter.LoggingHttpServletRequestWrapper;
import com.seasolutions.stock_management.model.support.servlet_filter.LoggingHttpServletResponseWrapper;
import com.seasolutions.stock_management.model.support.logging.LoggingRequest;
import com.seasolutions.stock_management.model.support.logging.LoggingResponse;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static org.eclipse.jetty.util.StringUtil.isNotBlank;


@Log4j2
public class LoggingFilter implements Filter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private int maxContentSize;

    private Set<String> excludedPaths = emptySet();

    private String requestPrefix;

    private String responsePrefix;

    private final List<String> contentTypesToLogBody = new ArrayList<>();

    private final Pattern passwordMatcher = Pattern.compile("(?<=(password|newPassword|currentPassword)\": ?\")(.*?)(?=\")");

    static {
        OBJECT_MAPPER.setSerializationInclusion(Include.NON_EMPTY);
    }

    public LoggingFilter() {
        this(Builder.create());
    }

    public LoggingFilter(final Builder builder) {
        requireNonNull(builder, "builder must not be null");

        this.maxContentSize = builder.maxContentSize;
        this.excludedPaths = builder.excludedPaths;
        this.requestPrefix = builder.requestPrefix;
        this.responsePrefix = builder.responsePrefix;
        contentTypesToLogBody.add("text/");
        contentTypesToLogBody.add("application/json");
        contentTypesToLogBody.add("application/xml");
        contentTypesToLogBody.add("application/svg+xml");
        contentTypesToLogBody.add("application/xhtml+xml");
        contentTypesToLogBody.add("application/svg+xml");
    }




    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

        final String maxContentSize = filterConfig.getInitParameter("maxContentSize");
        if (maxContentSize != null) {
            this.maxContentSize = parseInt(maxContentSize);
        }

        final String excludedPaths = filterConfig.getInitParameter("excludedPaths");
        if (isNotBlank(excludedPaths)) {
            final String[] paths = excludedPaths.split("\\s*,\\s*");
            this.excludedPaths = new HashSet<>(asList(paths));
        }

        final String requestPrefix = filterConfig.getInitParameter("requestPrefix");
        if (isNotBlank(requestPrefix)) {
            this.requestPrefix = requestPrefix;
        }

        final String responsePrefix = filterConfig.getInitParameter("responsePrefix");
        if (isNotBlank(responsePrefix)) {
            this.responsePrefix = responsePrefix;
        }
    }





    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            //throw new ServletException("LoggingFilter just supports HTTP requests");
            filterChain.doFilter(request, response);
            return;
        }

        if (!log.isInfoEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        final LoggingHttpServletRequestWrapper httpRequestWrapper = new LoggingHttpServletRequestWrapper((HttpServletRequest) request);

        final LoggingHttpServletResponseWrapper httpResponseWrapper = new LoggingHttpServletResponseWrapper((HttpServletResponse) response);

        log.info(requestPrefix + getRequestDescription(httpRequestWrapper));
        log.info("\n\n");
        filterChain.doFilter(httpRequestWrapper, httpResponseWrapper);
        log.info(responsePrefix + getResponseDescription(httpRequestWrapper, httpResponseWrapper));
        log.info("\n\n");

        if (!((HttpServletResponse) response).isCommitted()) {
            response.getOutputStream().write(httpResponseWrapper.getContentAsBytes());
        } else {
            try {
                response.getOutputStream().write(httpResponseWrapper.getContentAsBytes());
            } catch (final IllegalStateException e) {
                log.warn("outputStream was already acquired from HttpServletResponse.");
            }
            log.info("Response was already committed.");
        }
    }



    protected String getRequestDescription(final LoggingHttpServletRequestWrapper requestWrapper) {
        final LoggingRequest loggingRequest = new LoggingRequest();
        loggingRequest.setSender(requestWrapper.getLocalAddr());
        loggingRequest.setMethod(requestWrapper.getMethod());
        loggingRequest.setPath(requestWrapper.getRequestURI());
        if (requestWrapper.isFormPost()) {
            loggingRequest.setParams(null);
        } else {
            loggingRequest.setParams(requestWrapper.getParameters());
        }
        loggingRequest.setHeaders(requestWrapper.getHeaders());
        final String content = requestWrapper.getContent();

        if (isTextual(requestWrapper.getContentType())) {
            final String obfuscatedContent = passwordMatcher.matcher(content).replaceAll("**********");
            if (log.isTraceEnabled()) {
                loggingRequest.setBody(obfuscatedContent);
            } else {
                loggingRequest.setBody(obfuscatedContent.substring(0, Math.min(obfuscatedContent.length(), maxContentSize)));
            }
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(loggingRequest);
        } catch (final JsonProcessingException e) {
            log.warn("Cannot serialize Request to JSON", e);
            return null;
        }
    }


    protected String getResponseDescription(final LoggingHttpServletRequestWrapper requestWrapper,
                                            final LoggingHttpServletResponseWrapper responseWrapper) {
        final LoggingResponse loggingResponse = new LoggingResponse();
        loggingResponse.setUrl(String.format("%s %s%s",
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                requestWrapper.getQueryString() != null && !requestWrapper.getQueryString().isEmpty() ? "?" + requestWrapper.getQueryString() : ""));
        loggingResponse.setStatus(responseWrapper.getStatus());
        loggingResponse.setHeaders(responseWrapper.getHeaders());
        final String content = responseWrapper.getContent();
        if (isTextual(responseWrapper.getContentType())) {
            if (log.isTraceEnabled()) {
                loggingResponse.setBody(content);
            } else {
                loggingResponse.setBody(content.substring(0, Math.min(content.length(), maxContentSize)));
            }
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(loggingResponse);
        } catch (final JsonProcessingException e) {
            log.warn("Cannot serialize Response to JSON", e);
            return null;
        }
    }
    private boolean isTextual(final String mimeOrContentType) {
        if (mimeOrContentType == null) {
            return false;
        }
        for (final String textContentType : contentTypesToLogBody) {
            if (mimeOrContentType.contains(textContentType)) {
                return true;
            }
        }
        return false;
    }




    @Override
    public void destroy() {
    }



    public static class Builder {

        public static final int DEFAULT_MAX_CONTENT_SIZE = 1024;
        private String loggerName = LoggingFilter.class.getName();

        private int maxContentSize = DEFAULT_MAX_CONTENT_SIZE;

        private Set<String> excludedPaths = emptySet();

        private String requestPrefix = "REQUEST: ";

        private String responsePrefix = "RESPONSE: ";

        public static Builder create() {
            return new Builder();
        }

        public void loggerName(final String loggerName) {
            requireNonNull(loggerName, "loggerName must not be null");
            this.loggerName = loggerName;
        }

        public Builder maxContentSize(final int maxContentSize) {
            this.maxContentSize = maxContentSize;
            return this;
        }

        public Builder excludedPaths(final String... excludedPaths) {
            requireNonNull(excludedPaths, "excludedPaths must not be null");
            this.excludedPaths = Stream.of(excludedPaths).collect(Collectors.toSet());
            return this;
        }

        public void requestPrefix(final String requestPrefix) {
            requireNonNull(requestPrefix, "requestPrefix must not be null");
            this.requestPrefix = requestPrefix;
        }

        public void responsePrefix(final String responsePrefix) {
            requireNonNull(responsePrefix, "responsePrefix must not be null");
            this.responsePrefix = responsePrefix;
        }
    }

}
