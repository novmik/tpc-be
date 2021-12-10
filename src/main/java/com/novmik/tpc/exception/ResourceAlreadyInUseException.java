package com.novmik.tpc.exception;

import java.io.Serial;
import lombok.Getter;

/**
 * Exception при регистрации клиента
 * (email занят).
 */
@Getter
public class ResourceAlreadyInUseException extends RuntimeException {

  /**
   * Имя ресурса.
   */
  private final String resourceName;
  /**
   * Имя поля.
   */
  private final String fieldName;
  /**
   * Значение поля.
   */
  private final transient Object fieldValue;
  /**
   * SerialVersionUID.
   */
  @Serial
  private static final long serialVersionUID = -1982473178513820064L;

  /**
   * Ctor.
   *
   * @param resourceName email
   * @param fieldName    адрес
   * @param fieldValue   новый email
   */
  public ResourceAlreadyInUseException(final String resourceName,
      final String fieldName,
      final Object fieldValue) {
    super(String.format("%s already in use with %s : '%s'", resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}