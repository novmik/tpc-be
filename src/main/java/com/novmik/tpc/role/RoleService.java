package com.novmik.tpc.role;

import static com.novmik.tpc.role.RoleConstant.ROLE_OR_PRIVILEGE_NOT_EXISTS;

import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.privilege.PrivilegeService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {

  private final RoleRepository roleRepository;
  private final PrivilegeService privilegeService;

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  public Optional<Role> getRoleByName(final String roleName) {
    return roleRepository.findRoleByName(roleName);
  }

  public Role addNewRole(final Role role) {
    return roleRepository.save(role);
  }

  public void addPrivilegeToRole(final String roleName, final String privilegeName) {
    Optional<Role> roleByName = getRoleByName(roleName);
    Optional<Privilege> privilegeByName = privilegeService.findByPrivilegeName(privilegeName);
    if (roleByName.isPresent() && privilegeByName.isPresent()) {
      roleByName.get().getPrivileges().add(privilegeByName.get());
    } else {
      throw new NotFoundException(ROLE_OR_PRIVILEGE_NOT_EXISTS);
    }
  }

}
