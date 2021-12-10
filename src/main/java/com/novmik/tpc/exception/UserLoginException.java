package com.novmik.tpc.exception;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

import java.io.Serial;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception при login.
 */
@ResponseStatus(EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6212884541672845210L;

  /**
   * Ctor.
   *
   * @param message сообщение
   */
  public UserLoginException(final String message) {
    super(message);
  }

  /**
   * Ctor.
   *
   * @param message сообщение
   * @param cause случай
   */
  public UserLoginException(final String message, final Throwable cause) {
    super(message, cause);
  }
}