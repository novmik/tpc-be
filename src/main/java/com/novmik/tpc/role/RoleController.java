package com.novmik.tpc.role;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Роль control layer.
 * Доступ с 'ROLE_ADMIN'
 */
@AllArgsConstructor
@RequestMapping("api/v1/role")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class RoleController {

  /**
   * RoleService {@link RoleService}.
   */
  private final RoleService roleService;

  /**
   * Список ролей.
   * Get-запрос "api/v1/role"
   *
   * @return список ролей {@link Role}
   */
  @GetMapping
  public ResponseEntity<List<Role>> getAllRoles() {
    return new ResponseEntity<>(roleService.getAllRoles(), OK);
  }

  /**
   * Добавление роли.
   * Post-запрос "api/v1/role"
   *
   * @param role роль {@link Role}
   * @return роль {@link Role}
   */
  @PostMapping
  public ResponseEntity<Role> addNewRole(@RequestBody final Role role) {
    return new ResponseEntity<>(roleService.addNewRole(role), CREATED);
  }

  /**
   * Удаление {@link Role}.
   * Delete-запрос "api/v1/role/{idRole}"
   *
   * @param idRole id {@link Role}
   */
  @DeleteMapping("/{idRole}")
  public void deleteRoleById(@PathVariable("idRole") final Long idRole) {
    roleService.deleteRoleById(idRole);
  }

  /**
   * Добавление полномочия роли.
   * Post-запрос "api/v1/role/addprivilege"
   *
   * @param privilegeToRole {@link AddPrivilegeToRoleRequest}
   */
  @PostMapping("/addprivilege")
  public void addPrivilegeToRole(
      @RequestBody final AddPrivilegeToRoleRequest privilegeToRole) {
    roleService.addPrivilegeToRole(privilegeToRole.getRoleName(),
        privilegeToRole.getPrivilegeName());
  }
}
