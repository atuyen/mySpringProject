package com.seasolutions.stock_management.config;

import com.seasolutions.stock_management.model.exception.*;
import com.seasolutions.stock_management.model.support.response_wrapper.*;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@Log4j2
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({
            NotFoundException.class, ObjectNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundException(final Exception e, final WebRequest request) {
        return handleException(e, request, new NotFoundResponseWrapper(e.getMessage()), HttpStatus.NOT_FOUND, false);
    }

    @ExceptionHandler({
            BadArgumentException.class
    })
    public ResponseEntity<Object> handleBadArgumentException(final BadArgumentException e, final WebRequest request) {
        return handleException(e, request, new BadRequestResponseWrapper(e.getData()), HttpStatus.BAD_REQUEST, false);
    }
    @ExceptionHandler({
           ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleBadArgumentException(final ConstraintViolationException e, final WebRequest request) {
        return handleException(e, request, new BadRequestResponseWrapper(null,e.getMessage()), HttpStatus.BAD_REQUEST, false);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException e, final WebRequest request) {
        return handleException(e, request, new BadRequestResponseWrapper(), HttpStatus.BAD_REQUEST, false);
    }


    @ExceptionHandler({
            InternalServerErrorException.class
    })
    public ResponseEntity<Object> handleInternalServerErrorException(final InternalServerErrorException e, final WebRequest request) {
        return handleException(e, request, new BadRequestResponseWrapper(e.getMessage()), HttpStatus.BAD_REQUEST, false);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(final Exception e, final WebRequest request) {
        return handleException(e, request, new UnAuthenticatedResponseWrapper(e.getMessage()), HttpStatus.UNAUTHORIZED, false);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(final Exception e, final WebRequest request) {
        return handleException(e, request, new UnauthorizedRequestResponseWrapper(e.getMessage()), HttpStatus.UNAUTHORIZED, false);
    }

    @ExceptionHandler({
            Exception.class, RuntimeException.class
    })
    public ResponseEntity<Object> handleGenericException(final Exception e, final WebRequest request) {
        try {
            sendErrorToServer(e);
        } catch (Throwable t) {
            logger.error("Failed to send slack server error message", t);
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
            final boolean logDetailException) {
        if (logDetailException) {
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
        if (!(body instanceof BaseResponseWrapper)) {
            actualBody = new FailedResponseWrapper(e.getMessage());
        }
        return super.handleExceptionInternal(e, actualBody, headers, status, request);
    }



    private void logException(final Throwable e) {
       log.error(e.getMessage(),e);
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

        log.error(builder.toString());
    }




}
