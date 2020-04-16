package com.seasolutions.stock_management.security.config;

import com.seasolutions.stock_management.security.annotation.Authorized;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import com.seasolutions.stock_management.security.authorization.Authorization;
import com.seasolutions.stock_management.security.authorization.MethodInvoker;
import com.seasolutions.stock_management.security.filter.AuthenticationExclusion;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;


@Configuration
public class SecurityPostProcessor implements BeanPostProcessor {
    @Autowired
    private ApplicationContext applicationContext;


    private final Set<AuthenticationExclusion> unsecuredPaths = new HashSet<>();
    private final Map<Method, MethodInvoker> authorizationMethods = new HashMap<>();

    @Bean(name = "unsecuredPaths")
    public Set<AuthenticationExclusion> getUnsecuredPaths() {
        return unsecuredPaths;
    }

    @Bean(name = "authorizedMethods")
    public Map<Method, MethodInvoker> getAuthorizedMethods() {
        return authorizationMethods;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        final Class<?> clazz = bean.getClass();
        final RestController annotation = clazz.getAnnotation(RestController.class);
        if (annotation != null) {
            populateUnsecuredMethods(clazz);
            populateAuthorizedMethods(clazz);
        }
        return bean;
    }

    private void populateUnsecuredMethods(final Class<?> clazz) {
        final Set<AuthenticationExclusion> unsecuredPaths = getUnsecuredPaths(clazz);
        if (!unsecuredPaths.isEmpty()) {
            this.unsecuredPaths.addAll(getUnsecuredPaths(clazz));
        }
    }

    private Set<AuthenticationExclusion> getUnsecuredPaths(final Class<?> clazz) {
        return Arrays.stream(clazz.getMethods()).filter(method ->
                Modifier.isPublic(method.getModifiers()) &&
                        method.getAnnotation(Unauthenticated.class) != null
        )
                .map(method -> {
                    AnnotationAttributes annotationAttributes =
                            AnnotatedElementUtils.findMergedAnnotationAttributes(method, RequestMapping.class, false, false);
                    if (annotationAttributes != null) {
                        final RequestMethod requestMethod = ((RequestMethod[]) annotationAttributes.get("method"))[0];
                        final String requestPath = ((String[]) annotationAttributes.get("path"))[0];
                        return new AuthenticationExclusion(requestMethod, requestPath);
                    }
                    throw new RuntimeException("Method annotated with @Unauthenticated " +
                            "must also be annotated with @RequestMapping.");
                })
                .collect(Collectors.toSet());
    }

    private void populateAuthorizedMethods(final Class<?> clazz) {
        final Authorized authorized = clazz.getAnnotation(Authorized.class);
        if (authorized != null) {
            final List<String> missingAuthorizations = new ArrayList<>();
            final Class<? extends Authorization> authorizationClazz = authorized.value();
            final Object authorizationBean = applicationContext.getBean(authorizationClazz);
            Arrays.stream(clazz.getMethods())
                    .filter(this::shouldAuthorizeMethod)
                    .forEach(method -> {
                        try {
                            final Method authorizationMethod =
                                    authorizationClazz.getMethod(method.getName(), method.getParameterTypes());
                            if (authorizationMethod.getReturnType() == boolean.class) {
                                authorizationMethods.put(method, new MethodInvoker(authorizationBean, authorizationMethod));
                            } else {
                                throw new RuntimeException(
                                        String.format("The return type of authorization method[%s#%s] is not boolean.",
                                                authorizationClazz.getCanonicalName(), authorizationMethod.getName()));
                            }
                        } catch (final NoSuchMethodException e) {
                            missingAuthorizations.add(String.format("    public boolean %s(%s) {\n        return true;\n    }", method.getName(),
                                    Arrays.stream(method.getGenericParameterTypes())
                                            .map(t -> t.getTypeName() + " arg")
                                            .collect(Collectors.joining(", "))));
                        }
                    });
            if (missingAuthorizations.size() > 0) {
                final StringBuilder message = new StringBuilder("Missing following methods in " +
                        authorizationClazz.getCanonicalName() + "\n\n");
                message.append(missingAuthorizations.stream().collect(Collectors.joining("\n")));
                throw new RuntimeException(
                        String.format("Missing following methods \n%s", message.toString()));

            }
        }

    }

    private boolean shouldAuthorizeMethod(final Method method) {
        return
                Modifier.isPublic(method.getModifiers()) &&
                        method.getAnnotation(Unauthenticated.class) == null && (
                        method.getAnnotation(GetMapping.class) != null ||
                                method.getAnnotation(PutMapping.class) != null ||
                                method.getAnnotation(PostMapping.class) != null ||
                                method.getAnnotation(DeleteMapping.class) != null ||
                                method.getAnnotation(PatchMapping.class) != null
                );
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }


}
