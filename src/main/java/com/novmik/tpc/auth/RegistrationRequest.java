package com.novmik.tpc.auth;

import com.novmik.tpc.role.Role;
import java.util.Collection;
import lombok.Data;

@Data
public class RegistrationRequest {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Collection<Role> roles;
}
