package com.seasolutions.stock_management.security.token;

import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenPayload {
    EmployeeViewModel employeeViewModel;
}
