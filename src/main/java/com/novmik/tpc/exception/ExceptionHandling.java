package com.novmik.tpc.exception;

import static com.novmik.tpc.exception.ExceptionConstant.ACCOUNT_DISABLED;
import static com.novmik.tpc.exception.ExceptionConstant.ACCOUNT_LOCKED;
import static com.novmik.tpc.exception.ExceptionConstant.ERROR_PROCESSING_FILE;
import static com.novmik.tpc.exception.ExceptionConstant.INCORRECT_CREDENTIALS;
import static com.novmik.tpc.exception.ExceptionConstant.INTERNAL_SERVER_ERROR_MSG;
import static com.novmik.tpc.exception.ExceptionConstant.METHOD_IS_NOT_ALLOWED;
import static com.novmik.tpc.exception.ExceptionConstant.NOT_ENOUGH_PERMISSION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.util.Objects;
import javax.persistence.NoResultException;
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
  public ResponseEntity<HttpResponse> handleUserRegistrationException(
      final UserRegistrationException ex, final WebRequest request) {
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
  public ResponseEntity<HttpResponse> handleInvalidTokenRequestException(
      final InvalidTokenRequestException exception) {
    return createHttpResponse(NOT_ACCEPTABLE, exception.getMessage());
  }

  @ExceptionHandler(UpdatePasswordException.class)
  public ResponseEntity<HttpResponse> handleUpdatePasswordException(
      final UpdatePasswordException exception) {
    return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
  }

  @ExceptionHandler(TokenRefreshException.class)
  public ResponseEntity<HttpResponse> handleTokenRefreshException(
      final TokenRefreshException exception) {
    return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
  }

  @ExceptionHandler(ResourceAlreadyInUseException.class)
  public ResponseEntity<HttpResponse> handleResourceAlreadyInUseException(
      final ResourceAlreadyInUseException exception) {
    return createHttpResponse(CONFLICT, exception.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<HttpResponse> handleBadRequestException(
      final BadRequestException exception) {
    return createHttpResponse(BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(UserLoginException.class)
  public ResponseEntity<HttpResponse> handleUserLoginException(final UserLoginException exception) {
    return createHttpResponse(EXPECTATION_FAILED, exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<HttpResponse> handleNotFoundException(final NotFoundException exception) {
    return createHttpResponse(NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<HttpResponse> handleHttpRequestMethodNotSupportedException(
      final HttpRequestMethodNotSupportedException exception) {
    HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods())
        .iterator().next();
    return createHttpResponse(METHOD_NOT_ALLOWED,
        String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> handleException(final Exception exception) {
    log.error(exception.getMessage());
    return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<HttpResponse> handleNoResultException(final NoResultException exception) {
    log.error(exception.getMessage());
    return createHttpResponse(NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<HttpResponse> handleIoException(final IOException exception) {
    log.error(exception.getMessage());
    return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
  }

  private ResponseEntity<HttpResponse> createHttpResponse(final HttpStatus httpStatus,
      final String message) {
    return new ResponseEntity<>(new HttpResponse(
        httpStatus.value(),
        httpStatus,
        httpStatus.getReasonPhrase(),
        message), httpStatus);
  }

}
