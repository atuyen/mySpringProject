package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.exception.AuthenticationException;
import com.seasolutions.stock_management.model.exception.NotFoundException;
import com.seasolutions.stock_management.model.security.LoginRequest;
import com.seasolutions.stock_management.model.security.LoginResponse;
import com.seasolutions.stock_management.model.support.enumerable.LoginStatuses;
import com.seasolutions.stock_management.model.support.response_wrapper.DefaultResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.ResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.UnAuthenticatedResponseWrapper;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.model.view_model.UserSessionViewModel;
import com.seasolutions.stock_management.service.interfaces.IEmployeeService;
import com.seasolutions.stock_management.service.interfaces.ILoginService;
import com.seasolutions.stock_management.service.interfaces.IUserSessionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Log4j2
@RestController
//@RequestMapping(path = "/sessions")
public class SessionController {
    private static final String COOKIE_AUTH_TOKEN = "token";

    @Autowired
    ILoginService loginService;

    @Autowired
    IUserSessionService userSessionService;

    @Autowired
    IEmployeeService employeeService;
    
    @PostMapping(path = "/sessions")
    public ResponseWrapper<LoginResponse> startSession(@RequestBody final LoginRequest loginRequest,
                                                       final HttpServletRequest httpRequest,
                                                       final HttpServletResponse httpResponse) {
        loginRequest.setIpAddress(httpRequest.getRemoteAddr());
        final LoginResponse loginResponse = loginService.login(loginRequest);
        return new DefaultResponseWrapper<>(loginResponse);
    }


    @PutMapping(path = "/{employeeId}")
    public ResponseWrapper<LoginResponse> refreshSession(@PathVariable("employeeId") final long employeeId,
                                                         @RequestHeader("Authorization") String authToken) {
        final LoginResponse loginResponse = new LoginResponse();
        final EmployeeViewModel employee = employeeService.findById(employeeId);
        if (employee != null) {
            final UserSessionViewModel refreshedUserSession = userSessionService.refreshToken(authToken, employee);
            loginResponse.setSession(refreshedUserSession);
            loginResponse.setAccountInfo(employee);
            return new DefaultResponseWrapper<>(loginResponse);
        }
        throw new NotFoundException("No account found for: " + employeeId);
    }

    @DeleteMapping
    public ResponseWrapper<Void> closeSession(
            @RequestHeader("Authorization") String authToken
    ) {
        userSessionService.deleteSessionByToken(authToken);
        return new DefaultResponseWrapper<Void>(null);
    }


}
