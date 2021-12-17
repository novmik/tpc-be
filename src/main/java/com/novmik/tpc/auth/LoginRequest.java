package com.novmik.tpc.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Запрос логин.
 */
@Data
public class LoginRequest {

  /**
   * Email клиента.
   */
  private String email;
  /**
   * Пароль.
   */
  private String password;
}
