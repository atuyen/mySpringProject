package com.seasolutions.stock_management.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seasolutions.stock_management.model.support.enumerable.LoginStatuses;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.model.view_model.UserSessionViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    @JsonIgnore
    private String message;

    @JsonIgnore
    private LoginStatuses status;

    private EmployeeViewModel accountInfo;

    private UserSessionViewModel session;

    public LoginResponse(final LoginStatuses status, final String message) {
        this.status = status;
        this.message = message;
    }


}
