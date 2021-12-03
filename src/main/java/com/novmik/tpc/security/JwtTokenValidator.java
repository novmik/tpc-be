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
      log.error("Invalid JWT signature");
      throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");

    } catch (final MalformedJwtException ex) {
      log.error("Invalid JWT token");
      throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

    } catch (final ExpiredJwtException ex) {
      log.error("Expired JWT token");
      throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

    } catch (final UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
      throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

    } catch (final IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
      throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
    }
    return true;
  }
}
