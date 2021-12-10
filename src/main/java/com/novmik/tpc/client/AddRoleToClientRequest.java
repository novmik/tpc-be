package com.novmik.tpc.client;

import lombok.Data;

/**
 * Запрос добавления роли клиенту.
 */
@Data
public class AddRoleToClientRequest {

  /**
   * Email.
   */
  private String email;
  /**
   * Наименование роли.
   */
  private String roleName;
}
