package com.novmik.tpc.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Запрос на изменение пароля.
 */
@AllArgsConstructor
@Data
public class UpdatePasswordRequest {

  /**
   * Старый пароль.
   */
  private String oldPassword;
  /**
   * Новый пароль.
   */
  private String newPassword;
}
