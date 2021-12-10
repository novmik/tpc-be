package com.novmik.tpc.auth;

import lombok.Data;

/**
 * Запрос на обновление токена.
 */
@Data
public class TokenRefreshRequest {

  /**
   * Токен обновления.
   */
  private String refreshToken;
}
