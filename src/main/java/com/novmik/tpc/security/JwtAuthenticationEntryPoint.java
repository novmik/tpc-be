package com.novmik.tpc.security;

import static com.novmik.tpc.security.WebSecurityConfigConstant.FORBIDDEN_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.exception.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

  @Override
  public void commence(final HttpServletRequest request, HttpServletResponse response,
      final AuthenticationException exception) throws IOException {
    HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), FORBIDDEN,
        FORBIDDEN.getReasonPhrase().toUpperCase(), FORBIDDEN_MESSAGE);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(FORBIDDEN.value());
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(outputStream, httpResponse);
    outputStream.flush();
  }
}