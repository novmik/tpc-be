package com.novmik.tpc.security;

import com.novmik.tpc.client.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Создание и обработка токена.
 */
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.BeanMembersShouldSerialize"})
public class JwtTokenProvider {

  /**
   * Секрет.
   */
  private final String jwtSecret;
  /**
   * Продолжительность действия токена.
   */
  private final long jwtExpirationInMs;

  /**
   * Ctor.
   *
   * @param jwtSecret         секрет
   * @param jwtExpirationInMs продолжительность
   *                          действия токена
   */
  public JwtTokenProvider(@Value("${jwt.secret}") final String jwtSecret,
      @Value("${jwt.expiration}") final long jwtExpirationInMs) {
    this.jwtSecret = jwtSecret;
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  /**
   * Создание токена.
   *
   * @param customUserDetails {@link CustomUserDetails}
   * @return токен
   */
  public String generateToken(final CustomUserDetails customUserDetails) {
    final Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
    final String permissions = getClientPermissions(customUserDetails);
    return Jwts.builder()
        .setSubject(customUserDetails.getUsername())
        .setAudience("User Management Portal of Treatment Payment Calculator(TPC) (Novmik.com)")
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(expiryDate))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .claim("permissions", permissions)
        .compact();
  }

  /**
   * Получение email из токена.
   *
   * @param token токен
   * @return email
   */
  public String getSubjectFromJwt(final String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody().getSubject();
  }

  /**
   * Получение прав доступа.
   *
   * @param token токен
   * @return список {@link GrantedAuthority}
   */
  public List<GrantedAuthority> getPermissionsFromJwt(final String token) {
    final Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    return Arrays.stream(claims.get("permissions").toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  /**
   * Получение authorities.
   *
   * @param customUserDetails {@link CustomUserDetails}
   * @return строку authorities
   */
  private String getClientPermissions(final CustomUserDetails customUserDetails) {
    return customUserDetails
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
  }
}
