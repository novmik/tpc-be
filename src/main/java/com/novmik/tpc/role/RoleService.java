package com.novmik.tpc.role;

import static com.novmik.tpc.role.RoleConstants.ROLE_EXISTS;
import static com.novmik.tpc.role.RoleConstants.ROLE_NOT_CORRECT;
import static com.novmik.tpc.role.RoleConstants.ROLE_NOT_EXISTS;
import static com.novmik.tpc.role.RoleConstants.ROLE_OR_PRIVILEGE_NOT_EXISTS;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.privilege.PrivilegeService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * Роль business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class RoleService {

  /**
   * RoleRepository {@link RoleRepository}.
   */
  private final RoleRepository roleRepository;
  /**
   * PrivilegeService {@link PrivilegeService}.
   */
  private final PrivilegeService privilegeService;

  /**
   * Список ролей {@link Role}.
   *
   * @return спиоск ролей {@link Role}
   */
  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  /**
   * Поиск роли по наименованию.
   *
   * @param roleName наименование роли
   * @return роль {@link Role}
   * @throws NotFoundException если роль не найдена
   */
  public Optional<Role> findRoleByName(final String roleName) {
    final Optional<Role> roleByName = roleRepository.findRoleByName(roleName);
    if (roleByName.isEmpty()) {
      throw new NotFoundException(ROLE_NOT_EXISTS + roleName);
    }
    return roleByName;
  }

  /**
   * Добавление роли.
   *
   * @param role роль {@link Role}
   * @return роль {@link Role}
   * @throws BadRequestException если некорректные данные
   * @throws BadRequestException если роль есть
   */
  protected Role addNewRole(final Role role) {
    if (ObjectUtils.anyNull(
        role,
        role.getName(),
        role.getPrivileges()
    )) {
      throw new BadRequestException(ROLE_NOT_CORRECT + role);
    }
    if (findRoleByName(role.getName()).isPresent()) {
      throw new BadRequestException(ROLE_EXISTS + role.getName());
    }
    return save(role);
  }

  /**
   * Сохранение роли.
   *
   * @param role роль {@link Role}
   * @return роль {@link Role}
   */
  protected Role save(final Role role) {
    return roleRepository.save(role);
  }

  /**
   * Добавление полномочия роли.
   *
   * @param roleName      наименование роли
   * @param privilegeName наименование полномочия
   * @throws NotFoundException если роль
   *                           или полномочия не найдены
   */
  public void addPrivilegeToRole(final String roleName, final String privilegeName) {
    final Optional<Role> roleByName = findRoleByName(roleName);
    final Optional<Privilege> privilegeByName = privilegeService.findByPrivilegeName(privilegeName);
    if (roleByName.isPresent() && privilegeByName.isPresent()) {
      roleByName.get().getPrivileges().add(privilegeByName.get());
    } else {
      throw new NotFoundException(ROLE_OR_PRIVILEGE_NOT_EXISTS);
    }
  }

}
