package com.seasolutions.stock_management.repository.interfaces;

import com.seasolutions.stock_management.model.entity.UserSession;
import com.seasolutions.stock_management.repository.base.IBaseRepository;

import java.util.List;

public interface IUserSessionRepository extends IBaseRepository<UserSession> {
    UserSession findUserSessionByToken(String token);
    void deleteExpiredSessions();
    List<UserSession> deleteSessionsByTalentId(long talentId);

    void deleteSessionByToken(String authToken);

}
