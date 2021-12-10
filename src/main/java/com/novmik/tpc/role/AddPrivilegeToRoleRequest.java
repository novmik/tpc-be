package com.novmik.tpc.role;

import lombok.Data;

/**
 * Запрос добавление полномочия роли.
 */
@Data
public class AddPrivilegeToRoleRequest {

  /**
   * Наименование роли.
   */
  private String roleName;
  /**
   * Наименование полномочия.
   */
  private String privilegeName;
}
