package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Валидация токена Exception.
 */
public class InvalidTokenRequestException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5564626245818627044L;

  /**
   * Ctor.
   *
   * @param tokenType тип токена
   * @param token токен
   * @param message сообщение
   */
  public InvalidTokenRequestException(final String tokenType,
      final String token,
      final String message) {
    super(String.format("%s: [%s] token: [%s]", message, tokenType, token));
  }
}
