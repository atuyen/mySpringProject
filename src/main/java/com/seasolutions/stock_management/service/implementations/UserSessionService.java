package com.seasolutions.stock_management.service.implementations;

import com.google.common.cache.Cache;
import com.seasolutions.stock_management.model.entity.UserSession;
import com.seasolutions.stock_management.model.exception.NotFoundException;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import com.seasolutions.stock_management.model.view_model.UserSessionViewModel;
import com.seasolutions.stock_management.repository.interfaces.IUserSessionRepository;
import com.seasolutions.stock_management.security.token.Tokenizer;
import com.seasolutions.stock_management.service.interfaces.IUserSessionService;
import com.seasolutions.stock_management.util.MappingUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Log4j2
@Transactional
@Service
public class UserSessionService implements IUserSessionService {

    @Value("${security.session.timeToLiveInSeconds}")
    private long sessionTimeToLiveInSeconds =0;
    private int CONCURRENCY_LEVEL = 5;
    private long MAX_CACHE_SIZE = 100;
    private long CACHE_TIME_EXPIRY_IN_MINUTES = 1;
    private Cache<String, UserSession> sessionCache = CacheBuilder.newBuilder()
            .concurrencyLevel(CONCURRENCY_LEVEL)
            .maximumSize(MAX_CACHE_SIZE)
            .expireAfterWrite(CACHE_TIME_EXPIRY_IN_MINUTES, TimeUnit.MINUTES)
            .build();
    @Autowired
    IUserSessionRepository userSessionRepository;

    @Autowired
    Tokenizer tokenizer;

    @Override
    public UserSession registerSession(EmployeeViewModel employeeViewModel, String ipAddress) {


        try {
            UserSession userSession = UserSession.builder()
                    .employeeId(employeeViewModel.getId())
                    .ipAddress(ipAddress)
                    .created(new Date())
                    .expires(createExpirationDate())
                    .authToken(tokenizer.getToken(employeeViewModel))
                    .build();
            userSession = userSessionRepository.add(userSession);
            sessionCache.put(userSession.getAuthToken(), userSession);
            return userSession;
        } catch (Exception e) {
           throw new RuntimeException("Failed to create user session.",e);
        }
    }

    @Override
    public boolean isAuthenticated(String authToken, String ipAddress) {
       UserSession userSession = getUserSessionByToken(authToken);
        if(userSession==null){
            log.debug("isAuth: Didn't find userSession for talentId: $talentId, token: $authToken");
            return  false;
        }
        if(!ipAddress.equals(userSession.getIpAddress())){
            log.debug("isAuth: IP addresses doesn't match.");
            return  false;
        }
        if(userSession.getExpires().getTime()<System.currentTimeMillis()){
            log.debug("isAuth: IP addresses doesn't match.");
            return  false;
        }
        return  true;
    }

    @Override
    public void deleteExpiredSessions() {
        userSessionRepository.deleteExpiredSessions();
    }

    @Override
    public void deleteSessionsByTalentId(long talentId) {
        List<UserSession> userSessions = userSessionRepository.deleteSessionsByTalentId(talentId);
        userSessions.forEach(u->sessionCache.invalidate(u.getAuthToken()));
    }

    @Override
    public UserSessionViewModel refreshToken(String token,EmployeeViewModel employee) {
        UserSession userSession = userSessionRepository.findUserSessionByToken(token);
        if(userSession!=null){
            userSession.setExpires(createExpirationDate());
            String newToken = tokenizer.getToken(employee);
            userSession.setAuthToken(newToken);
            userSessionRepository.update(userSession);
            sessionCache.invalidate(token);
            sessionCache.put(newToken,userSession);
            UserSessionViewModel userSessionViewModel = MappingUtils.map(userSession,UserSessionViewModel.class);
            return userSessionViewModel;
        }

        throw new NotFoundException("No session found for token");

    }

    @Override
    public void deleteSessionByToken(String authToken) {
        sessionCache.invalidate(authToken);
        userSessionRepository.deleteSessionByToken(authToken);
    }

    private UserSession getUserSessionByToken(String token){
        UserSession userSession = sessionCache.getIfPresent(token);
        if(userSession!=null){
            return userSession;
        }
        userSession = userSessionRepository.findUserSessionByToken(token);
        if(userSession!=null){
            sessionCache.put(userSession.getAuthToken(),userSession);
            return userSession;
        }
        return null;
    }

    private Date createExpirationDate(){
        return new  Date(System.currentTimeMillis() + sessionTimeToLiveInSeconds * 1000);
    }

}
