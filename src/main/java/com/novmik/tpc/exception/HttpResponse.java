package com.novmik.tpc.exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class HttpResponse {

  private String timeStamp;
  private int httpStatusCode;
  private HttpStatus httpStatus;
  private String reason;
  private String message;

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
