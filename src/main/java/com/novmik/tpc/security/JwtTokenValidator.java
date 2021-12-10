package com.novmik.tpc.security;

import static com.novmik.tpc.security.SecurityConstants.JWT;

import com.novmik.tpc.exception.InvalidTokenRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Проверка токена.
 */
@Slf4j
@Component
@SuppressWarnings({"PMD.PreserveStackTrace", "PMD.LawOfDemeter",
    "PMD.BeanMembersShouldSerialize", "PMD.CyclomaticComplexity"})
public class JwtTokenValidator {

  /**
   * Секрет.
   */
  private final String jwtSecret;

  /**
   * Ctor.
   *
   * @param jwtSecret секрет
   */
  @Autowired
  public JwtTokenValidator(@Value("${jwt.secret}") final String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  /**
   * Проверка токена.
   *
   * @param authToken токен
   * @return boolean
   * @throws InvalidTokenRequestException если Exception
   */
  public boolean validateToken(final String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

    } catch (final SignatureException ex) {
      log.error("Неверный JWT ключ");
      log.trace("Неверный JWT ключ, trace: ", ex);
      throw new InvalidTokenRequestException(JWT, authToken, "Неверный ключ");

    } catch (final MalformedJwtException ex) {
      log.error("Неверный JWT token");
      log.trace("Неверный JWT token, trace: ", ex);
      throw new InvalidTokenRequestException(JWT, authToken, "Искажённый jwt token");

    } catch (final ExpiredJwtException ex) {
      log.error("Срок действия токена JWT истёк");
      log.trace("Срок действия токена JWT истёк, trace: ", ex);
      throw new InvalidTokenRequestException(
          JWT, authToken, "Срок действия токена истёк. Требуется обновление");

    } catch (final UnsupportedJwtException ex) {
      log.error("Неподдерживаемый токен JWT");
      log.trace("Неподдерживаемый токен JWT, trace: ", ex);
      throw new InvalidTokenRequestException(JWT, authToken, "Неподдерживаемый токен JWT");

    } catch (final IllegalArgumentException ex) {
      log.error("JWT claims строка пуста.");
      log.trace("JWT claims строка пуста, trace: ", ex);
      throw new InvalidTokenRequestException(JWT, authToken, "Illegal argument token");
    }
    return true;
  }
}
