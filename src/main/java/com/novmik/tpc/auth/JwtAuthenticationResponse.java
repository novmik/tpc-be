package com.novmik.tpc.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
