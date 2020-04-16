package com.seasolutions.stock_management.service.interfaces;

import com.seasolutions.stock_management.model.security.LoginRequest;
import com.seasolutions.stock_management.model.security.LoginResponse;

public interface ILoginService {
    LoginResponse login(LoginRequest loginRequest);
}
