package com.novmik.tpc.auth;

import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.exception.TokenRefreshException;
import com.novmik.tpc.exception.UserLoginException;
import com.novmik.tpc.refreshtoken.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Не удалось войти пользователю [" + loginRequest + "]"));
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("Авторизованный пользователь [API]: " + customUserDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return new ResponseEntity<>(new JwtAuthenticationResponse(jwtToken, refreshToken), OK);
                })
                .orElseThrow(() -> new UserLoginException("Не создать refresh token для: [" + loginRequest + "]"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshJwtToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Создан новый Jwt Auth token: " + updatedToken);
                    return new ResponseEntity<>(new JwtAuthenticationResponse(updatedToken, refreshToken), OK);
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "Неожиданная ошибка при обновлении токена. Пожалуйста, перезайдите."));
    }
}
