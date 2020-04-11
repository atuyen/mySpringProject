package com.seasolutions.stock_management.config;

import com.seasolutions.stock_management.exception.NotFoundException;
import com.seasolutions.stock_management.model.support.response_wrapper.*;
import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler({
            NotFoundException.class, ObjectNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundException(final Exception e, final WebRequest request) {
        return handleException(e, request, new NotFoundResponseWrapper(e.getMessage()), HttpStatus.NOT_FOUND, false);
    }









    @ExceptionHandler({
            Exception.class, RuntimeException.class
    })
    public ResponseEntity<Object> handleGenericException(final Exception e, final WebRequest request) {
        try {
            sendErrorToServer(e);
        } catch (Throwable t) {
            logger.error("Failed to send error to server", t);
        }
        return handleException(e, request, new ExceptionResponseWrapper(e), HttpStatus.INTERNAL_SERVER_ERROR, true);
    }


    private void sendErrorToServer(Exception e){

    }






    @Override
    protected ResponseEntity<Object> handleBindException(final BindException e, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleException(e, request, new BadRequestResponseWrapper(), HttpStatus.BAD_REQUEST, true);
    }


    private ResponseEntity<Object> handleException(
            final Exception e,
            final WebRequest request,
            final Object responseData,
            final HttpStatus statusCode,
            final boolean logException) {
        if (logException) {
            this.logException(e);
        } else {
            this.logExceptionSlim(e);
        }
        return handleExceptionInternal(e, responseData, new HttpHeaders(), statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception e,
                                                             final Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        Object actualBody = body;
        if (!(body instanceof WrapperResponse)) {
            actualBody = new FailedResponseWrapper(e.getMessage());
        }
        return super.handleExceptionInternal(e, actualBody, headers, status, request);
    }



    private void logException(final Throwable e) {
        if (logger.isDebugEnabled()) {
            logger.debug(e.getMessage(), e);
        } else if (logger.isWarnEnabled()) {
            logger.warn(e.getMessage(), e);
        } else {
            logger.error(e.getMessage(), e);
        }
    }

    private void logExceptionSlim(final Throwable e) {
        Throwable current = e;
        final StringBuilder builder = new StringBuilder();
        int i = 0;
        do {
            if (current == null) {
                break;
            }
            builder.append(current.getClass().getSimpleName() + " :: " + current.getMessage() + "\n");
            final String[] stackFrames = ExceptionUtils.getStackFrames(current);
            for (int j = 0; j < stackFrames.length && j < 4; j++) {
                builder.append("    " + stackFrames[j] + "\n");
            }
            builder.append("        ... " + (stackFrames.length - 4) + " more lines ...");
            current = current.getCause();
            i++;
        } while (current != null && i < 5);

        if (logger.isDebugEnabled()) {
            logger.debug(builder.toString());
        } else if (logger.isWarnEnabled()) {
            logger.warn(builder.toString());
        } else {
            logger.error(builder.toString());
        }
    }




}
