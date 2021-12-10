package com.novmik.tpc.auth;

import lombok.Data;

/**
 * Запрос на изменение пароля.
 */
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
