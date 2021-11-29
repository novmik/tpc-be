package com.novmik.tpc.client;

import lombok.Data;

@Data
public class AddRoleToClientRequest {

    private String email;
    private String roleName;
}
