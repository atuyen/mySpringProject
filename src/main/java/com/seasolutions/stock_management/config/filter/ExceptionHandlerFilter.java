package com.seasolutions.stock_management.config.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seasolutions.stock_management.model.exception.AuthenticationException;
import com.seasolutions.stock_management.model.exception.AuthorizationException;
import com.seasolutions.stock_management.model.support.response_wrapper.FailedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.UnAuthenticatedResponseWrapper;
import com.seasolutions.stock_management.model.support.response_wrapper.UnauthorizedRequestResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.util.NestedServletException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ExceptionHandlerFilter implements Filter {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (final AuthorizationException | NestedServletException e) {
            if (e instanceof AuthorizationException || e.getCause() instanceof AuthorizationException) {
                httpResponse.getWriter().write(
                    OBJECT_MAPPER.writeValueAsString(new UnauthorizedRequestResponseWrapper()));
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                throw e;
            }
        } catch (final AuthenticationException e) {
            httpResponse.getWriter().write(
                OBJECT_MAPPER.writeValueAsString(new UnAuthenticatedResponseWrapper()));
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (final Throwable t) {
            if (!(t.getCause() instanceof AuthorizationException)) {
                log.error("Request failed", t.getCause() != null ? t.getCause() : t);
            }
            if (!httpResponse.isCommitted()) {
                httpResponse.getWriter().write(
                    OBJECT_MAPPER.writeValueAsString(new FailedResponseWrapper()));
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public void destroy() { }
}
