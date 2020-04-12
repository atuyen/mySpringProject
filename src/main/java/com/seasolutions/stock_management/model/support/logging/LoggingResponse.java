package com.seasolutions.stock_management.model.support.logging;

import lombok.*;

import java.io.Serializable;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggingResponse implements Serializable {

    private static final long serialVersionUID = -6692682176015358216L;

    private int status;
    private String url;
    private Map<String, String> headers;
    private String body;
}
