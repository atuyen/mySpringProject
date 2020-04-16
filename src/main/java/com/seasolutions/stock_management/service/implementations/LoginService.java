package com.seasolutions.stock_management.service.implementations;

import com.seasolutions.stock_management.model.entity.Employee;
import com.seasolutions.stock_management.model.entity.UserSession;
import com.seasolutions.stock_management.model.exception.AuthenticationException;
import com.seasolutions.stock_management.model.security.LoginRequest;
import com.seasolutions.stock_management.model.security.LoginResponse;
import com.seasolutions.stock_management.model.support.enumerable.LoginStatuses;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.model.view_model.UserSessionViewModel;
import com.seasolutions.stock_management.service.interfaces.IEmployeeService;
import com.seasolutions.stock_management.service.interfaces.ILoginService;
import com.seasolutions.stock_management.service.interfaces.IUserSessionService;
import com.seasolutions.stock_management.util.EncodeUtils;
import com.seasolutions.stock_management.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements ILoginService {
    @Value("${auth.allowMultipleSessions}")
    private boolean allowMultipleSessions;

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IUserSessionService userSessionService;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Employee employee = employeeService.findByEmail(loginRequest.getUsername());
        if(employee==null){
            throw new AuthenticationException("Email is not exits");
        }
        if(!validatePassWord(employee.getPassword(),loginRequest.getPassword())){
            throw new AuthenticationException("Password is not true");
        }

        if(employee.getInvites().stream().filter(i ->i.getIsActive()).count()==0){
            throw new AuthenticationException("Account is not active");
        }



        if (!allowMultipleSessions) {
            userSessionService.deleteSessionsByTalentId(employee.getId());
        } else {
            userSessionService.deleteExpiredSessions();
        }

        final LoginResponse response = new LoginResponse(LoginStatuses.SUCCESS, "login.ok");
        EmployeeViewModel employeeViewModel = MappingUtils.map(employee, EmployeeViewModel.class);
        response.setAccountInfo(employeeViewModel);
        UserSession userSession = userSessionService.registerSession(employeeViewModel,loginRequest.getIpAddress());
        UserSessionViewModel userSessionViewModel = MappingUtils.map(userSession,UserSessionViewModel.class);
        response.setSession(userSessionViewModel);
        return response;
    }



    private boolean validatePassWord(String encryptedPassword,String plainPassword){

        return  EncodeUtils.comparePlaneTextAndEncryptedText(plainPassword,encryptedPassword);
    }

}
