package com.seasolutions.stock_management.model.support.servlet_filter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private static final String METHOD_POST = "POST";

    private byte[] content;

    private final Map<String, String[]> parameterMap;

    private final HttpServletRequest delegate;

    public LoggingHttpServletRequestWrapper(final HttpServletRequest request) {
        super(request);
        this.delegate = request;
        if (isFormPost()) {
            this.parameterMap = request.getParameterMap();
        } else {
            this.parameterMap = Collections.emptyMap();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (ArrayUtils.isEmpty(content)) {
            return delegate.getInputStream();
        }
        return new LoggingServletInputStream(content);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (ArrayUtils.isEmpty(content)) {
            return delegate.getReader();
        }
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public String getParameter(final String name) {
        if (ArrayUtils.isEmpty(content) || this.parameterMap.isEmpty()) {
            return super.getParameter(name);
        }
        final String[] values = this.parameterMap.get(name);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return Arrays.toString(values);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (ArrayUtils.isEmpty(content) || this.parameterMap.isEmpty()) {
            return super.getParameterMap();
        }
        return this.parameterMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (ArrayUtils.isEmpty(content) || this.parameterMap.isEmpty()) {
            return super.getParameterNames();
        }
        return new ParamNameEnumeration(this.parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        if (ArrayUtils.isEmpty(content) || this.parameterMap.isEmpty()) {
            return super.getParameterValues(name);
        }
        return this.parameterMap.get(name);
    }

    @SuppressWarnings("checkstyle:avoidinlineconditionals")
    public String getContent() {
        try {
            if (this.parameterMap.isEmpty()) {
                content = IOUtils.toByteArray(delegate.getInputStream());
            } else {
                content = getContentFromParameterMap(this.parameterMap);
            }
            final String requestEncoding = delegate.getCharacterEncoding();
            final String normalizedContent = StringUtils.normalizeSpace(
                    new String(content, requestEncoding != null ? requestEncoding : StandardCharsets.UTF_8.name()));
            return StringUtils.isBlank(normalizedContent) ? "[EMPTY]" : normalizedContent;
        } catch (final IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    @SuppressWarnings("checkstyle:avoidinlineconditionals")
    private byte[] getContentFromParameterMap(final Map<String, String[]> parameterMap) {
        return parameterMap.entrySet().stream().map(e -> {
            String[] value = e.getValue();
            return e.getKey() + "=" + (value.length == 1 ? value[0] : Arrays.toString(value));
        }).collect(Collectors.joining("&")).getBytes();
    }

    public Map<String, String> getHeaders() {
        final Map<String, String> headers = new HashMap<>(0);
        final Enumeration<String> headerNames = getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            if (headerName != null) {
                headers.put(headerName, getHeader(headerName));
            }
        }
        return headers;
    }

    @SuppressWarnings("checkstyle:avoidinlineconditionals")
    public Map<String, String> getParameters() {
        return getParameterMap().entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> {
            final String[] values = e.getValue();
            return values.length > 0 ? values[0] : "[EMPTY]";
        }));
    }

    public boolean isFormPost() {
        final String contentType = getContentType();
        return (contentType != null && contentType.contains(FORM_CONTENT_TYPE) && METHOD_POST.equalsIgnoreCase(getMethod()));
    }

    @SuppressWarnings("checkstyle:avoidinlineconditionals")
    private class ParamNameEnumeration implements Enumeration<String> {

        private final Iterator<String> iterator;

        private ParamNameEnumeration(final Set<String> values) {
            this.iterator = values != null ? values.iterator() : Collections.emptyIterator();
        }

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public String nextElement() {
            return iterator.next();
        }
    }

    private class LoggingServletInputStream extends ServletInputStream {

        private final InputStream is;

        private LoggingServletInputStream(final byte[] content) {
            this.is = new ByteArrayInputStream(content);
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(final ReadListener readListener) {
        }

        @Override
        public int read() throws IOException {
            return this.is.read();
        }

        @Override
        public void close() throws IOException {
            super.close();
            is.close();
        }
    }
}
