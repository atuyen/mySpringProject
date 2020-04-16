package com.seasolutions.stock_management.security.filter;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationExclusion {

    private final String path;
    private final RequestMethod method;
    private final Pattern pattern;

    public AuthenticationExclusion(final RequestMethod method, final String path) {
        this.method = method;
        this.path = path;
        final String pattern = String.format("^.*%s$", path.replaceAll("\\{[^/]+\\}", ".+"));
        this.pattern = Pattern.compile(pattern);
    }

    public boolean matches(final HttpServletRequest req) {
        final String methodName = req.getMethod();
        if (method.name().equalsIgnoreCase(methodName)) {
            final String uri = req.getRequestURI();
            return matches(uri);
        }
        return false;
    }

    private boolean matches(final String uri) {
        final Matcher matcher = this.pattern.matcher(uri);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}
