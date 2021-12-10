package com.novmik.tpc.client;

/**
 * Constants для пакета client.
 */
@SuppressWarnings("PMD.LongVariable")
public class ClientConstants {

  /**
   * CLIENT_NOT_FOUND_BY_EMAIL.
   */
  public static final String CLIENT_NOT_FOUND_BY_EMAIL =
      "Пользователя не найдено с электронной почтой: ";
  /**
   * EMAIL_ADDRESS_EXISTS.
   */
  public static final String EMAIL_ADDRESS_EXISTS =
      "Запись уже существует с электронной почтой: ";
  /**
   * CLIENT_NOT_CORRECT.
   */
  public static final String CLIENT_NOT_CORRECT =
      "Некорректные данные о Клиенте.";
  /**
   * CLIENT_NOT_EXISTS.
   */
  public static final String CLIENT_NOT_EXISTS =
      "Клиента с таким id/email не существует: ";
  /**
   * CLIENT_OR_ROLE_NOT_EXISTS.
   */
  public static final String CLIENT_OR_ROLE_NOT_EXISTS =
      "Клиента или роли не существует.";
}
