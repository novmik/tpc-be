package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Не верный запрос Exception.
 */
public class BadRequestException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5242151676341088734L;

  /**
   * Ctor.
   *
   * @param message сообщение
   */
  public BadRequestException(final String message) {
    super(message);
  }
}
