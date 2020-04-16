package com.seasolutions.stock_management.service.interfaces;
import com.seasolutions.stock_management.model.entity.UserSession;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.model.view_model.UserSessionViewModel;

public interface IUserSessionService {
    UserSession registerSession(EmployeeViewModel employeeViewModel, String ipAddress);
    boolean isAuthenticated( String authToken, String ipAddress);
    void deleteExpiredSessions();
    void deleteSessionsByTalentId(long talentId);
    UserSessionViewModel refreshToken(String token,EmployeeViewModel employee);
    void deleteSessionByToken(String authToken);


}
