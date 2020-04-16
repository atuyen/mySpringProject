package com.seasolutions.stock_management.model.security;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
    private String ipAddress;
}
