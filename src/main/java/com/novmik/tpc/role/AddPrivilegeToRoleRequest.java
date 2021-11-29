package com.novmik.tpc.role;

import lombok.Data;

@Data
public class AddPrivilegeToRoleRequest {
    private String roleName;
    private String privilegeName;
}
