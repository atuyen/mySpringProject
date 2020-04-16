package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.UserSession;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IUserSessionRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Repository
public class UserSessionRepository extends BaseRepository<UserSession> implements IUserSessionRepository {
    String baseSelectQuery = "Select s from UserSession s ";
    String baseDeleteQuery = "Delete  from UserSession s ";

    @Override
    public UserSession findUserSessionByToken(String token) {
        String query = baseSelectQuery+" where s.authToken = :authToken";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("authToken",token);
        List<UserSession> userSessions = findDataByQuery(query,params);
        return  userSessions.size()>0?userSessions.get(0):null;
    }

    @Override
    public void deleteExpiredSessions() {
        String deleteQuery = baseDeleteQuery+ " where s.expires < :now  ";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("now",new Date());
        executeQuery(deleteQuery,params);
    }

    @Override
    public List<UserSession> deleteSessionsByTalentId(long talentId) {
        String conditions = " where s.employeeId = :employeeId ";
        String query = baseSelectQuery+conditions;
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("employeeId",talentId);
        List<UserSession> userSessions = findDataByQuery(query,params);

        //---------------------------------------------------------------
        String deleteQuery = baseDeleteQuery+ conditions;
        executeQuery(deleteQuery,params);
        return  userSessions;
    }

    @Override
    public void deleteSessionByToken(String authToken) {
        String conditions = " where s.authToken = :authToken ";
        String deleteQuery = baseDeleteQuery+ conditions;
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("authToken",authToken);
        executeQuery(deleteQuery,params);
    }
}
