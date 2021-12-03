package com.novmik.tpc.security;

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

@Slf4j
@Component
public class JwtTokenValidator {

  private final String jwtSecret;

  @Autowired
  public JwtTokenValidator(@Value("${jwt.secret}") String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

    } catch (final SignatureException ex) {
      log.error("Неверный JWT ключ");
      throw new InvalidTokenRequestException("JWT", authToken, "Неверный ключ");

    } catch (final MalformedJwtException ex) {
      log.error("Неверный JWT token");
      throw new InvalidTokenRequestException("JWT", authToken, "Искажённый jwt token");

    } catch (final ExpiredJwtException ex) {
      log.error("Срок действия токена JWT истёк");
      throw new InvalidTokenRequestException(
          "JWT", authToken, "Срок действия токена истёк. Требуется обновление");

    } catch (final UnsupportedJwtException ex) {
      log.error("Неподдерживаемый токен JWT");
      throw new InvalidTokenRequestException("JWT", authToken, "Неподдерживаемый токен JWT");

    } catch (final IllegalArgumentException ex) {
      log.error("JWT claims строка пуста.");
      throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
    }
    return true;
  }
}
