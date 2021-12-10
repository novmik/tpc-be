package com.novmik.tpc.exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Настраиваемый Http ответ.
 */
@NoArgsConstructor
@Data
public class HttpResponse {

  /**
   * Время.
   */
  private String timeStamp;
  /**
   * HttpStatusCode.
   */
  private int httpStatusCode;
  /**
   * HttpStatus.
   */
  private HttpStatus httpStatus;
  /**
   * Причина.
   */
  private String reason;
  /**
   * Сообщение.
   */
  private String message;

  /**
   * Ctor.
   *
   * @param httpStatusCode httpStatus код
   * @param httpStatus httpStatus
   * @param reason причина
   * @param message сообщение
   */
  public HttpResponse(final int httpStatusCode,
      final HttpStatus httpStatus,
      final String reason,
      final String message) {
    this.timeStamp = new SimpleDateFormat("dd.MM.yyyy H:mm:ss zzz").format(new Date());
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.reason = reason;
    this.message = message;
  }
}
