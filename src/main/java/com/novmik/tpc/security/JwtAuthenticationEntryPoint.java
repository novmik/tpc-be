package com.novmik.tpc.security;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.exception.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Вызывается при попытке
 * получить доступ к защищенному
 * ресурсу REST без предоставления
 * каких-либо учетных данных.
 */
@Component
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException exception) throws IOException {
    final HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), FORBIDDEN,
        FORBIDDEN.getReasonPhrase().toUpperCase(Locale.ROOT),
        "Вам необходимо войти в систему, чтобы получить доступ к этой странице!.");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(FORBIDDEN.value());
    try (OutputStream outputStream = response.getOutputStream()) {
      final ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(outputStream, httpResponse);
      outputStream.flush();
    }
  }
}