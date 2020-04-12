package com.seasolutions.stock_management.model.support.logging;

import lombok.*;

import java.io.Serializable;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggingRequest implements Serializable {

    private static final long serialVersionUID = -4702574169916528738L;

    private String sender;

    private String method;

    private String path;

    private Map<String, String> params;

    private Map<String, String> headers;

    private String body;


    public void setHeaders(final Map<String, String> headers) {
        replaceHeader("Cookie", 15, headers);
        replaceHeader("User-Agent", 40, headers);
        this.headers = headers;
    }

    private void replaceHeader(final String headerName, final int maxLength, final Map<String, String> headers) {
        final String headerValue = headers.get(headerName);
        if (headerValue != null) {
            final int end = Math.min(headerValue.length(), maxLength);
            final String truncatedHeaderValue = headerValue.substring(0, end) + "...";
            headers.put(headerName, truncatedHeaderValue);
        }
    }

}
