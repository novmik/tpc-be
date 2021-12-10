package com.novmik.tpc.security;

import static com.novmik.tpc.security.SecurityConstants.TOKEN_REQUEST_HEADER_PREFIX;
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


/**
 * Фильтр запроса для authentication
 * с помощью jwt-токен.
 */
@Slf4j
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter",
    "PMD.BeanMembersShouldSerialize", "PMD.AvoidCatchingGenericException"})
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  /**
   * {@link JwtTokenProvider}.
   */
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  /**
   * {@link JwtTokenValidator}.
   */
  @Autowired
  private JwtTokenValidator jwtTokenValidator;
  /**
   * {@link CustomUserDetailsService}.
   */
  @Autowired
  private CustomUserDetailsService detailsService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain) throws ServletException, IOException {
    if (!"/api/v1/auth/login".equals(request.getServletPath()) && !"/api/v1/auth/refresh"
        .equals(request.getServletPath())) {
      try {
        final String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtTokenValidator.validateToken(jwt)) {
          final String clientEmail = jwtTokenProvider.getSubjectFromJwt(jwt);
          final UserDetails userDetails = detailsService.loadUserByUsername(clientEmail);
          final List<GrantedAuthority> authorities = jwtTokenProvider.getPermissionsFromJwt(jwt);
          final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
              userDetails, jwt, authorities);
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (Exception ex) {
        log.error("Не удалось установить аутентификацию пользователя в security context: ", ex);
        throw ex;
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(final HttpServletRequest request) {
    String jwtFromRequest;
    final String bearerToken = request.getHeader(AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_REQUEST_HEADER_PREFIX)) {
      jwtFromRequest = bearerToken.substring(TOKEN_REQUEST_HEADER_PREFIX.length());
    } else {
      jwtFromRequest = "";
    }
    return jwtFromRequest;
  }
}
