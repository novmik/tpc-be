package com.novmik.tpc.exception;

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

/**
 * Обработчик exceptions.
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.TooManyMethods",
    "PMD.MissingStaticMethodInNonInstantiatableClass"})
public final class ExceptionHandling {

  /**
   * Обработка {@link DisabledException}.
   *
   * @return {@link HttpResponse} с {@link HttpStatus} 400 и сообщением
   */
  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<HttpResponse> handleDisabledException() {
    return createHttpResponse(
        HttpStatus.BAD_REQUEST, "Ваш аккаунт заблокирован. Свяжитесь с администрацией.");
  }

  /**
   * Обработка {@link BadCredentialsException}.
   *
   * @return {@link HttpResponse} с {@link HttpStatus} 400 и сообщением
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<HttpResponse> handleBadCredentialsException() {
    return createHttpResponse(
        HttpStatus.BAD_REQUEST, "Неверно имя пользователя / пароль. Попробуйте снова.");
  }

  /**
   * Обработка {@link UserRegistrationException}.
   *
   * @param exception {@link UserRegistrationException}
   * @param request   {@link WebRequest}
   * @return {@link HttpResponse} с {@link HttpStatus} 417 и сообщением
   */
  @ExceptionHandler(UserRegistrationException.class)
  public ResponseEntity<HttpResponse> handleUserRegistrationException(
      final UserRegistrationException exception, final WebRequest request) {
    return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
  }

  /**
   * Обработка {@link AccessDeniedException}.
   *
   * @return {@link HttpResponse} с {@link HttpStatus} 403 и сообщением
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<HttpResponse> handleAccessDeniedException() {
    return createHttpResponse(HttpStatus.FORBIDDEN, "У вас недостаточно прав.");
  }

  /**
   * Обработка {@link LockedException}.
   *
   * @return {@link HttpResponse} с {@link HttpStatus} 401 и сообщением
   */
  @ExceptionHandler(LockedException.class)
  public ResponseEntity<HttpResponse> handleLockedException() {
    return createHttpResponse(
        HttpStatus.UNAUTHORIZED, "Ваш аккаунт заблокирован. Свяжитесь с администрацией.");
  }

  /**
   * Обработка {@link InvalidTokenRequestException}.
   *
   * @param exception {@link InvalidTokenRequestException}
   * @return {@link HttpResponse} с {@link HttpStatus} 406 и сообщением
   */
  @ExceptionHandler(InvalidTokenRequestException.class)
  public ResponseEntity<HttpResponse> handleInvalidTokenRequestException(
      final InvalidTokenRequestException exception) {
    return createHttpResponse(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
  }

  /**
   * Обработка {@link UpdatePasswordException}.
   *
   * @param exception {@link UpdatePasswordException}
   * @return {@link HttpResponse} с {@link HttpStatus} 401 и сообщением
   */
  @ExceptionHandler(UpdatePasswordException.class)
  public ResponseEntity<HttpResponse> handleUpdatePasswordException(
      final UpdatePasswordException exception) {
    return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
  }

  /**
   * Обработка {@link TokenRefreshException}.
   *
   * @param exception {@link TokenRefreshException}
   * @return {@link HttpResponse} с {@link HttpStatus} 417 и сообщением
   */
  @ExceptionHandler(TokenRefreshException.class)
  public ResponseEntity<HttpResponse> handleTokenRefreshException(
      final TokenRefreshException exception) {
    return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
  }

  /**
   * Обработка {@link ResourceAlreadyInUseException}.
   *
   * @param exception {@link ResourceAlreadyInUseException}
   * @return {@link HttpResponse} с {@link HttpStatus} 409 и сообщением
   */
  @ExceptionHandler(ResourceAlreadyInUseException.class)
  public ResponseEntity<HttpResponse> handleResourceAlreadyInUseException(
      final ResourceAlreadyInUseException exception) {
    return createHttpResponse(HttpStatus.CONFLICT, exception.getMessage());
  }

  /**
   * Обработка {@link BadRequestException}.
   *
   * @param exception {@link BadRequestException}
   * @return {@link HttpResponse} с {@link HttpStatus} 400 и сообщением
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<HttpResponse> handleBadRequestException(
      final BadRequestException exception) {
    return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
  }

  /**
   * Обработка {@link UserLoginException}.
   *
   * @param exception {@link UserLoginException}
   * @return {@link HttpResponse} с {@link HttpStatus} 417 и сообщением
   */
  @ExceptionHandler(UserLoginException.class)
  public ResponseEntity<HttpResponse> handleUserLoginException(final UserLoginException exception) {
    return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
  }

  /**
   * Обработка {@link NotFoundException}.
   *
   * @param exception {@link NotFoundException}
   * @return {@link HttpResponse} с {@link HttpStatus} 404 и сообщением
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<HttpResponse> handleNotFoundException(final NotFoundException exception) {
    return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
  }

  /**
   * Обработка {@link HttpRequestMethodNotSupportedException}.
   *
   * @param exception {@link HttpRequestMethodNotSupportedException}
   * @return {@link HttpResponse} с {@link HttpStatus} 405 и сообщением
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<HttpResponse> handleHttpRequestMethodNotSupportedException(
      final HttpRequestMethodNotSupportedException exception) {
    final HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods())
        .iterator().next();
    return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED,
        String.format("Метод запроса не разрешен. Отправьте '%s' запрос.", supportedMethod));
  }

  /**
   * Обработка {@link Exception}.
   *
   * @param exception {@link Exception}
   * @return {@link HttpResponse} с {@link HttpStatus} 500 и сообщением
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> handleException(final Exception exception) {
    if (log.isErrorEnabled()) {
      log.error(exception.getMessage());
    }
    return createHttpResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, "Произошла ошибка при обработке запроса.");
  }

  /**
   * Обработка {@link NoResultException}.
   *
   * @param exception {@link NoResultException}
   * @return {@link HttpResponse} с {@link HttpStatus} 404 и сообщением
   */
  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<HttpResponse> handleNoResultException(final NoResultException exception) {
    if (log.isErrorEnabled()) {
      log.error(exception.getMessage());
    }
    return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
  }

  /**
   * Обработка {@link IOException}.
   *
   * @param exception {@link IOException}
   * @return {@link HttpResponse} с {@link HttpStatus} 500 и сообщением
   */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<HttpResponse> handleIoException(final IOException exception) {
    if (log.isErrorEnabled()) {
      log.error(exception.getMessage());
    }
    return createHttpResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при обработке файла.");
  }

  private ResponseEntity<HttpResponse> createHttpResponse(final HttpStatus httpStatus,
      final String message) {
    return new ResponseEntity<>(new HttpResponse(
        httpStatus.value(),
        httpStatus,
        httpStatus.getReasonPhrase(),
        message), httpStatus);
  }

  private ExceptionHandling() {
  }
}
