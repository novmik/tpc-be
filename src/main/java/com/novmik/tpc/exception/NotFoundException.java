package com.novmik.tpc.exception;

import java.io.Serial;

/**
 * Отсутствие Exception.
 */
public class NotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -9044394091185824703L;

  /**
   * Ctor.
   *
   * @param message сообщение
   */
  public NotFoundException(final String message) {
    super(message);
  }
}
