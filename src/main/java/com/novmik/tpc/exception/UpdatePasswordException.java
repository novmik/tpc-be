package com.novmik.tpc.exception;

/**
 * Обновление пароля Exception.
 */
public class UpdatePasswordException extends RuntimeException {

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
