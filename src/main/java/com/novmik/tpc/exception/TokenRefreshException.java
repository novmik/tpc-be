package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Обновление токена Exception.
 */
public class TokenRefreshException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -3694291885258522181L;

  /**
   * Ctor.
   *
   * @param token токен
   * @param message сообщение
   */
  public TokenRefreshException(final String token, final String message) {
    super(String.format("Не обновился токен [%s]: [%s])", token, message));
  }
}
