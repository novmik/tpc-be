package com.novmik.tpc.role;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Запрос добавление полномочия роли.
 */
@Data
@AllArgsConstructor
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
