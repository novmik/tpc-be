package com.novmik.tpc.auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {

  private String refreshToken;
}
