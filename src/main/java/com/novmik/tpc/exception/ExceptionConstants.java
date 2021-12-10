package com.novmik.tpc.exception;

/**
 * Constant для пакета Exception.
 */
@SuppressWarnings("PMD.LongVariable")
public class ExceptionConstants {

  /**
   * ACCOUNT_LOCKED.
   */
  public static final String ACCOUNT_LOCKED =
      "Ваш аккаунт заблокирован. Свяжитесь с администрацией.";
  /**
   * METHOD_IS_NOT_ALLOWED.
   */
  public static final String METHOD_IS_NOT_ALLOWED =
      "Метод запроса не разрешен на этой конечной точке. Пожалуйста, отправьте '%s' запрос.";
  /**
   * INTERNAL_SERVER_ERROR_MSG.
   */
  public static final String INTERNAL_SERVER_ERROR_MSG =
      "Произошла ошибка при обработке запроса.";
  /**
   * INCORRECT_CREDENTIALS.
   */
  public static final String INCORRECT_CREDENTIALS =
      "Неверное имя пользователя / пароль. Попробуйте снова.";
  /**
   * ACCOUNT_DISABLED.
   */
  public static final String ACCOUNT_DISABLED =
      "Ваш аккаунт отключен. Свяжитесь с администрацией.";
  /**
   * ERROR_PROCESSING_FILE.
   */
  public static final String ERROR_PROCESSING_FILE =
      "Ошибка при обработке файла.";
  /**
   * NOT_ENOUGH_PERMISSION.
   */
  public static final String NOT_ENOUGH_PERMISSION =
      "У вас недостаточно прав.";
}
