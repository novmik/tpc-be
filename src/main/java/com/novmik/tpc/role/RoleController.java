package com.novmik.tpc.role;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/role")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class RoleController {

  private final RoleService roleService;

  @GetMapping
  public ResponseEntity<List<Role>> getAllRoles() {
    return new ResponseEntity<>(roleService.getAllRoles(), OK);
  }

  @PostMapping
  public ResponseEntity<Role> addNewRole(@RequestBody final Role role) {
    return new ResponseEntity<>(roleService.addNewRole(role), CREATED);
  }

  @PostMapping("/addprivilege")
  public void addPrivilegeToRole(
      @RequestBody final AddPrivilegeToRoleRequest addPrivilegeToRoleRequest) {
    roleService.addPrivilegeToRole(addPrivilegeToRoleRequest.getRoleName(),
        addPrivilegeToRoleRequest.getPrivilegeName());
  }

}
