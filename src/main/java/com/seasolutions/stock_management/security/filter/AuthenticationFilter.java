package com.seasolutions.stock_management.security.filter;

import com.seasolutions.stock_management.model.exception.AuthenticationException;
import com.seasolutions.stock_management.model.support.RequestData;
import com.seasolutions.stock_management.security.token.TokenPayload;
import com.seasolutions.stock_management.security.token.Tokenizer;
import com.seasolutions.stock_management.service.implementations.UserSessionService;
import com.seasolutions.stock_management.service.interfaces.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class AuthenticationFilter extends GenericFilterBean {

    private final ApplicationContext applicationContext;
    private IUserSessionService userSessionService;
    private Tokenizer tokenizer;

    private static final List<AuthenticationExclusion> HARDCODED_EXCLUSIONS = Arrays.asList(
            //exclude(RequestMethod.GET, ".*index.*"),
            //exclude(RequestMethod.GET, "/app.*"),
            //exclude(RequestMethod.GET, "/assets.*"),
//            exclude(RequestMethod.GET, "/css.*"),
//            exclude(RequestMethod.GET, "/swagger-resources"),
//            exclude(RequestMethod.GET, "/webjars/springfox-swagger-ui/lib.*"),
//            exclude(RequestMethod.GET, "/webjars/springfox-swagger-ui.*"),
//            exclude(RequestMethod.GET, "/swagger-ui.html"),
//            exclude(RequestMethod.GET, "/webjars"),
//            exclude(RequestMethod.GET, "/configuration/ui"),
//            exclude(RequestMethod.GET, "/images/favicon"),
//            exclude(RequestMethod.GET, "/api-docs"),
            exclude(RequestMethod.POST, "/sessions")
    );

    private List<AuthenticationExclusion> allAuthenticationExclusions;


    public AuthenticationFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void doFilter(
        final ServletRequest request,
        final ServletResponse response,
        final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (shouldAuthenticate(httpRequest)) {
            String token = getTokenFromRequest(httpRequest);
            TokenPayload tokenPayload=tokenizer.verifyTokenAndGetPayload(token);
            validateSession(token, httpRequest);
            setupRequestData(tokenPayload, httpRequest, httpResponse);
        }else {
            setupRequestData(null, httpRequest, httpResponse);

        }
        chain.doFilter(request, response);
    }


    private void validateSession(
                                 final String authToken,
                                 final HttpServletRequest request) {
        if (!getUserSessionService().isAuthenticated(
             authToken,
                request.getRemoteAddr())) {
            throw new AuthenticationException("No valid user session found.");
        }
    }

    private String getTokenFromRequest( HttpServletRequest httpRequest){
        String bearerToken = httpRequest.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.replace("Bearer ","");
//        }else {
//            throw  new AuthenticationException("Token is invalid");
//        }
        if(StringUtils.isEmpty(bearerToken)){
            throw  new AuthenticationException("Token is invalid");
        }
        return  bearerToken;
    }


    @Override
    protected void initFilterBean() throws ServletException {
        tokenizer=getTokenizer();
        userSessionService=getUserSessionService();
    }

    private static AuthenticationExclusion exclude(final RequestMethod method, final String path) {
        return new AuthenticationExclusion(method, path);
    }

    private boolean shouldAuthenticate(final HttpServletRequest httpRequest) {
        for (final AuthenticationExclusion exclusion : getAllAuthenticationExclusions()) {
            if (exclusion.matches(httpRequest)) {
                return false;
            }
        }
        return true;
    }

    private List<AuthenticationExclusion> getAllAuthenticationExclusions() {
        if (this.allAuthenticationExclusions == null) {
            this.allAuthenticationExclusions = new ArrayList<>();
            this.allAuthenticationExclusions.addAll(HARDCODED_EXCLUSIONS);

            final Set<AuthenticationExclusion> exclusions =
                    (Set<AuthenticationExclusion>) applicationContext.getBean("unsecuredPaths");
            this.allAuthenticationExclusions.addAll(exclusions);


        }
        return this.allAuthenticationExclusions;
    }


    private Tokenizer getTokenizer() {
        if (tokenizer == null) {
            tokenizer = applicationContext.getBean(Tokenizer.class);
        }
        return tokenizer;
    }

    private IUserSessionService getUserSessionService() {
        if (userSessionService == null) {
            userSessionService = applicationContext.getBean(UserSessionService.class);
        }
        return userSessionService;
    }

    private void  setupRequestData(final TokenPayload payload,
                                   final HttpServletRequest request,
                                   final HttpServletResponse response) {
        final RequestData requestData = (RequestData) applicationContext.getBean("requestData");
        requestData.setRequest(request);
        requestData.setResponse(response);
        requestData.setTokenPayload(payload);
    }






}
