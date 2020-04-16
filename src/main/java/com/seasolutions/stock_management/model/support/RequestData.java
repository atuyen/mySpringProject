package com.seasolutions.stock_management.model.support;

import com.seasolutions.stock_management.security.token.TokenPayload;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Getter
@Setter
public class RequestData {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private TokenPayload tokenPayload;

    public String getAuthToken() {
        if (request != null) {
            return request.getHeader("Authorization");
        }
        return null;
    }



}
