package com.novmik.tpc.role;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
    public void addPrivilegeToRole(@RequestBody final AddPrivilegeToRoleRequest addPrivilegeToRoleRequest) {
        roleService.addPrivilegeToRole(addPrivilegeToRoleRequest.getRoleName(), addPrivilegeToRoleRequest.getPrivilegeName());
    }

}
