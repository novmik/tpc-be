package com.novmik.tpc.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class JwtAccessDeniedHandlerTest {

  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private AccessDeniedException exception;
  @InjectMocks
  private JwtAccessDeniedHandler underTest;

  @Test
  void canHandle() throws IOException {
    ServletOutputStream mock = mock(ServletOutputStream.class);
    when(response.getOutputStream()).thenReturn(mock);
    underTest.handle(request, response, exception);
    verify(response)
        .setContentType(MediaType.APPLICATION_JSON_VALUE);
    verify(response)
        .setStatus(UNAUTHORIZED.value());
  }
}