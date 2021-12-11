package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Обновление пароля Exception.
 */
public class UpdatePasswordException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 8694256901669597873L;

  /**
   * Ctor.
   *
   * @param user клиент(email)
   * @param message сообщение
   */
  public UpdatePasswordException(final String user, final String message) {
    super(String.format("Couldn't update password for [%s]: [%s])", user, message));
  }
}
