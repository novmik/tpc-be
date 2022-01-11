package com.novmik.tpc.security;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.client.CustomUserDetailsService;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Mock
  private JwtTokenProvider jwtTokenProvider;
  @Mock
  private JwtTokenValidator jwtTokenValidator;
  @Mock
  private CustomUserDetailsService detailsService;
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private FilterChain filterChain;
  @InjectMocks
  private JwtAuthenticationFilter underTest = new JwtAuthenticationFilter();

  @Test
  void canDoFilterInternal() throws ServletException, IOException {
    underTest.doFilterInternal(request, response, filterChain);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void throwDoFilterWhenUriIsLoginAndRefresh() throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("/api/v1/auth/login");
    underTest.doFilterInternal(request, response, filterChain);
    verify(request, never()).getHeader(anyString());
    when(request.getServletPath()).thenReturn("/api/v1/auth/refresh");
    underTest.doFilterInternal(request, response, filterChain);
    verify(request, never()).getHeader(anyString());
  }

  @Test
  void canDoFilterInternalWithHeader() throws ServletException, IOException {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE");
    UserDetails userDetails = new CustomUserDetails(new Client());
    when(request.getServletPath()).thenReturn("/api/v1");
    when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer refreshToken");
    when(jwtTokenValidator.validateToken(anyString())).thenReturn(true);
    when(jwtTokenProvider.getSubjectFromJwt(anyString())).thenReturn("client@email.com");
    when(detailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
    when(jwtTokenProvider.getPermissionsFromJwt(anyString())).thenReturn(List.of(grantedAuthority));
    underTest.doFilterInternal(request, response, filterChain);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void canDoFilterInternalWithoutHeader() throws ServletException, IOException {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE");
    UserDetails userDetails = new CustomUserDetails(new Client());
    when(request.getServletPath()).thenReturn("/api/v1");
    when(request.getHeader(AUTHORIZATION)).thenReturn("");
    underTest.doFilterInternal(request, response, filterChain);
    verify(filterChain).doFilter(request, response);
  }
}