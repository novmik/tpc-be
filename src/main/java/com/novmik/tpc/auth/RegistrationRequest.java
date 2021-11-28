package com.novmik.tpc.auth;

import com.novmik.tpc.role.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Collection<Role> roles;}
