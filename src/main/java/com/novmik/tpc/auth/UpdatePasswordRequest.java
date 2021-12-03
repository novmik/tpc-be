package com.novmik.tpc.auth;

import lombok.Data;

@Data
public class UpdatePasswordRequest {

  private String oldPassword;
  private String newPassword;
}
