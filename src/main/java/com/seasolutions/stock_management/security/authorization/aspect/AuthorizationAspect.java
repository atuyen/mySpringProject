package com.seasolutions.stock_management.security.authorization.aspect;


import com.seasolutions.stock_management.model.exception.AuthorizationException;
import com.seasolutions.stock_management.model.support.RequestData;
import com.seasolutions.stock_management.security.annotation.Authorized;
import com.seasolutions.stock_management.security.authorization.Authorization;
import com.seasolutions.stock_management.security.authorization.MethodInvoker;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Class that executes authorization rules.
 * This class intercepts all calls to controller methods (for example GET /api/v1/companies/#21:0) and checks
 * if the method requires authorization, i.e. if it is annotated with {@link Authorized}.
 * It looks up a {@link Authorization} implementation, as specified by the {@link Authorized} annotation,
 * and invokes a method that exactly matches the controller method signature, with the exception of the
 * return type and calls that method with the same arguments.
 *
 * For example, if the controller method has the following signature:
 * <code>
 * ResponseWrapper<Company> getCompanies(final String companyId, final List<String> withs)
 * </code>
 * then the following method on the authorization implementation will be called:
 * <code>
 * boolean getCompanies(final String companyId, final List<String> withs)
 * </code>
 */
@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationAspect.class);

    @Autowired
    private Map<Method, MethodInvoker> authorizedMethods;

    @Autowired
    private RequestData requestData;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void patchMapping() { }

    @Before("getMapping() || postMapping() || putMapping() || deleteMapping() || patchMapping() || requestMapping()")
    public void authorize(final JoinPoint joinPoint) throws Throwable {
        final Method authorizedMethod = getControllerMethod(joinPoint);
        final MethodInvoker authorizationMethodInvoker = getAuthorizationMethodInvoker(authorizedMethod);
        if (authorizationMethodInvoker != null) {
            try {
                final boolean result = (boolean) authorizationMethodInvoker.invoke(joinPoint.getArgs());
                LOGGER.debug("<==== Authorization completed [" + result + "] ===========================================>");
                if (!result) {
                    throw new AuthorizationException(
                            String.format("User[%s] is not authorized to access: %s",
                                    requestData.getTokenPayload().getEmployeeViewModel().getId(),
                                    requestData.getRequest().getServletPath())
                    );
                }
            } catch (final IllegalAccessException e) {
                throw new RuntimeException("Unable to access authorization method", e);
            } catch (final InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }

    private MethodInvoker getAuthorizationMethodInvoker(final Method method) {
        return authorizedMethods.get(method);
    }

    private Method getControllerMethod(final JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

}
