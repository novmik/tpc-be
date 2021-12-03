package com.novmik.tpc.security;

import static com.novmik.tpc.security.JwtTokenConstant.TOKEN_REQUEST_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.novmik.tpc.client.CustomUserDetailsService;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;



@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private JwtTokenValidator jwtTokenValidator;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain) throws ServletException, IOException {
    if (!request.getServletPath().equals("/api/v1/auth/login") && !request.getServletPath()
        .equals("/api/v1/auth/refresh")) {
      try {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtTokenValidator.validateToken(jwt)) {
          String clientEmail = jwtTokenProvider.getSubjectFromJwt(jwt);
          UserDetails userDetails = customUserDetailsService.loadUserByUsername(clientEmail);
          List<GrantedAuthority> authorities = jwtTokenProvider.getPermissionsFromJwt(jwt);
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
              userDetails, jwt, authorities);
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (Exception ex) {
        log.error("Failed to set user authentication in security context: ", ex);
        throw ex;
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(final HttpServletRequest request) {
    String jwtFromRequest = null;
    String bearerToken = request.getHeader(AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_REQUEST_HEADER_PREFIX)) {
      log.info("Extracted Token: " + bearerToken);
      jwtFromRequest = bearerToken.substring(TOKEN_REQUEST_HEADER_PREFIX.length());
    }
    return jwtFromRequest;
  }
}
