package com.novmik.tpc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Objects;

import static com.novmik.tpc.exception.ExceptionConstant.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> handleDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> handleBadCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<HttpResponse> handleUserRegistrationException(UserRegistrationException ex, WebRequest request) {
        return createHttpResponse(EXPECTATION_FAILED, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> handleAccessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> handleLockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(InvalidTokenRequestException.class)
    public ResponseEntity<HttpResponse> handleInvalidTokenRequestException(InvalidTokenRequestException exception) {
        return createHttpResponse(NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(UpdatePasswordException.class)
    public ResponseEntity<HttpResponse> handleUpdatePasswordException(UpdatePasswordException exception) {
        return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<HttpResponse> handleTokenRefreshException(TokenRefreshException exception) {
        return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyInUseException.class)
    public ResponseEntity<HttpResponse> handleResourceAlreadyInUseException(ResourceAlreadyInUseException exception) {
        return createHttpResponse(CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpResponse> handleBadRequestException(BadRequestException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<HttpResponse> handleUserLoginException(UserLoginException exception) {
        return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpResponse> handleNotFoundException(NotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleException(Exception exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> handleNoResultException(NoResultException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND,exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> handleIOException(IOException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR,ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase(),
                message), httpStatus);
    }

}
