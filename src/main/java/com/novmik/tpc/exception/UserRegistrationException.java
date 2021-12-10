package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Exception при регистрации клиента
 * (не получилось зарегистрировать).
 */
public class UserRegistrationException extends RuntimeException {
  /**
   * SerialVersionUID.
   */
  @Serial
  private static final long serialVersionUID = -2672913668229795130L;

  /**
   * Ctor.
   *
   * @param user email клиента
   * @param message сообщение
   */
  public UserRegistrationException(final String user, final String message) {
    super(String.format("Failed to register User[%s] : '%s'", user, message));
  }
}