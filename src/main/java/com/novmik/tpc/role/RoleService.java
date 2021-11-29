package com.novmik.tpc.role;

import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.privilege.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.role.RoleConstant.ROLE_OR_PRIVILEGE_NOT_EXISTS;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;

    public List<Role> getAllRoles() { return roleRepository.findAll(); }

    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    public void addPrivilegeToRole(String roleName, String privilegeName) {
        Optional<Role> roleByName = getRoleByName(roleName);
        Optional<Privilege> privilegeByName = privilegeService.findByPrivilegeName(privilegeName);
        if (roleByName.isPresent() && privilegeByName.isPresent()) {
            roleByName.get().getPrivileges().add(privilegeByName.get());
        } else {
            throw new NotFoundException(ROLE_OR_PRIVILEGE_NOT_EXISTS);
        }
    }

}
