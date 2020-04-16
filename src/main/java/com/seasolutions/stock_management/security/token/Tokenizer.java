package com.seasolutions.stock_management.security.token;

import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;

public interface Tokenizer {
    String getToken(EmployeeViewModel employee);
    TokenPayload verifyTokenAndGetPayload(String token);

}
