package com.novmik.tpc.auth;

import static org.springframework.http.HttpStatus.OK;

import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.exception.TokenRefreshException;
import com.novmik.tpc.exception.UserLoginException;
import com.novmik.tpc.refreshtoken.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Auth control layer.
 */
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.GuardLogStatement"})
public class AuthController {

  /**
   * AuthService {@link AuthService}.
   */
  private final AuthService authService;

  /**
   * Логин.
   *
   * @param loginRequest {@link LoginRequest}
   * @return JwtAuthenticationResponse {@link JwtAuthenticationResponse}
   * @throws UserLoginException если ошибка
   */
  @PostMapping("/login")
  public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
      @RequestBody final LoginRequest loginRequest) {
    final Authentication authentication = authService.authenticateUser(loginRequest)
        .orElseThrow(
            () -> new UserLoginException("Не удалось войти пользователю [" + loginRequest + "]"));
    final CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authService.createAndPersistRefreshToken(authentication)
        .map(RefreshToken::getToken)
        .map(refreshToken -> {
          final String jwtToken = authService.generateToken(customUserDetails);
          return new ResponseEntity<>(new JwtAuthenticationResponse(jwtToken, refreshToken), OK);
        })
        .orElseThrow(
            () -> new UserLoginException("Не создать refresh token для: [" + loginRequest + "]"));
  }

  /**
   * Обновление токена.
   *
   * @param trRequest запрос обновления токена
   * @return JwtAuthenticationResponse {@link JwtAuthenticationResponse}
   * @throws TokenRefreshException если ошибка
   */
  @PostMapping("/refresh")
  public ResponseEntity<JwtAuthenticationResponse> refreshJwtToken(
      @RequestBody final TokenRefreshRequest trRequest) {

    return authService.refreshJwtToken(trRequest)
        .map(updatedToken -> {
          final String refreshToken = trRequest.getRefreshToken();
          return new ResponseEntity<>(new JwtAuthenticationResponse(updatedToken, refreshToken),
              OK);
        })
        .orElseThrow(() -> new TokenRefreshException(trRequest.getRefreshToken(),
            "Неожиданная ошибка при обновлении токена. Пожалуйста, перезайдите."));
  }
}
