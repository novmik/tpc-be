package com.novmik.tpc.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ответ с токенами.
 */
@AllArgsConstructor
@Data
public class JwtAuthenticationResponse {

  /**
   * Токен для доступа.
   */
  private String accessToken;
  /**
   * Токен для обновления.
   */
  private String refreshToken;
}
