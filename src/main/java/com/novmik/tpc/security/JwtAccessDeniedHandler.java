package com.novmik.tpc.security;

import static com.novmik.tpc.security.SecurityConstants.ACCESS_DENIED_MESSAGE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.exception.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Вызывается при отсутствии
 * необходимой authorization.
 */
@Component
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  /**
   * Обработка отказа в доступе.
   *
   * @param request   {@link HttpServletRequest}
   * @param response  {@link HttpServletResponse}
   * @param exception {@link AccessDeniedException}
   * @throws IOException если ошибка
   */
  @Override
  public void handle(final HttpServletRequest request, final HttpServletResponse response,
      final AccessDeniedException exception) throws IOException {
    final HttpResponse httpResponse = new HttpResponse(UNAUTHORIZED.value(), UNAUTHORIZED,
        UNAUTHORIZED.getReasonPhrase().toUpperCase(Locale.ROOT), ACCESS_DENIED_MESSAGE);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(UNAUTHORIZED.value());
    try (OutputStream outputStream = response.getOutputStream()) {
      final ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(outputStream, httpResponse);
      outputStream.flush();
    }
  }
}