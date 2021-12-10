package com.novmik.tpc.auth;

import static com.novmik.tpc.exception.ExceptionConstants.ACCOUNT_DISABLED;
import static com.novmik.tpc.exception.ExceptionConstants.ACCOUNT_LOCKED;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.ClientService;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.exception.TokenRefreshException;
import com.novmik.tpc.exception.UpdatePasswordException;
import com.novmik.tpc.refreshtoken.RefreshToken;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.security.JwtTokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Auth business interface layer.
 */
@RequiredArgsConstructor
@Service
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.GuardLogStatement"})
public class AuthService {

  /**
   * ClientService {@link ClientService}.
   */
  private final ClientService clientService;
  /**
   * JwtTokenProvider {@link JwtTokenProvider}.
   */
  private final JwtTokenProvider tokenProvider;
  /**
   * RefreshTokenService {@link RefreshTokenService}.
   */
  private final RefreshTokenService rtService;
  /**
   * PasswordEncoder {@link PasswordEncoder}.
   */
  private final PasswordEncoder passwordEncoder;
  /**
   * AuthenticationManager {@link AuthenticationManager}.
   */
  private final AuthenticationManager authManager;

  /**
   * Authentication клиента.
   * Check the principal of the
   * input authentication is valid and verified
   *
   * @param loginRequest логин запрос
   * @return Authentication {@link Authentication}
   */
  public Optional<Authentication> authenticateUser(final LoginRequest loginRequest) {
    return Optional.ofNullable(authManager
        .authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword())));
  }

  /**
   * Проверка password.
   * Password клиента с введенным
   *
   * @param currentUser клиент {@link Client}
   * @param password    пароль
   * @return boolean
   */
  private Boolean currentPasswordMatches(final Client currentUser,
      final String password) {
    return passwordEncoder.matches(password, currentUser.getPassword());
  }

  /**
   * Обновление пасспорта.
   *
   * @param customUserDetails {@link CustomUserDetails}
   * @param updatePassRequest запрос обноалвнеия пасспорта
   * @return клиент {@link Client}
   */
  public Optional<Client> updatePassword(final CustomUserDetails customUserDetails,
      final UpdatePasswordRequest updatePassRequest) {
    final String email = customUserDetails.getUsername();
    final Client currentUser = clientService.getClient(email)
        .orElseThrow();

    if (!currentPasswordMatches(currentUser, updatePassRequest.getOldPassword())) {
      throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
    }
    final String newPassword = passwordEncoder.encode(updatePassRequest.getNewPassword());
    currentUser.setPassword(newPassword);
    clientService.saveClient(currentUser);
    return Optional.of(currentUser);
  }

  /**
   * Создание токена.
   *
   * @param customUserDetails {@link CustomUserDetails}
   * @return токен
   */
  public String generateToken(final CustomUserDetails customUserDetails) {
    return tokenProvider.generateToken(customUserDetails);
  }

  /**
   * Создавание и сохранение RefreshToken.
   *
   * @param authentication {@link Authentication}
   * @return RefreshToken {@link RefreshToken}
   */
  public Optional<RefreshToken> createAndPersistRefreshToken(final Authentication authentication) {
    final Client currentUser = (Client) authentication.getPrincipal();
    RefreshToken refreshToken = rtService.createRefreshToken();
    refreshToken.setClient(currentUser);
    refreshToken = rtService.save(refreshToken);
    return Optional.ofNullable(refreshToken);
  }

  /**
   * Обновление токена.
   *
   * @param trRequest {@link TokenRefreshRequest}
   * @return токен
   * @throws TokenRefreshException если токен не найден
   */
  public Optional<String> refreshJwtToken(final TokenRefreshRequest trRequest) {
    final String strRefreshToken = trRequest.getRefreshToken();
    return Optional.of(rtService.findRefreshTokenByToken(strRefreshToken)
            .map(refreshToken -> {
              rtService.verifyExpiration(refreshToken);
              rtService.increaseCount(refreshToken);
              return refreshToken;
            })
            .map(RefreshToken::getClient)
            .map(this::checkClientAccess)
            .map(CustomUserDetails::new)
            .map(this::generateToken))
        .orElseThrow(() -> new TokenRefreshException(strRefreshToken,
            "Отсутствует refresh token. Пожалуйста, перезайдите."));
  }

  /**
   * Проверка доступа.
   *
   * @param client {@link Client}
   * @return client {@link Client}
   * @throws LockedException если клиент заблокирован
   * @throws DisabledException если клиент отключен
   */
  private Client checkClientAccess(final Client client) {
    if (!client.isNotLocked()) {
      throw new LockedException(ACCOUNT_LOCKED);
    }
    if (!client.isEnabled()) {
      throw new DisabledException(ACCOUNT_DISABLED);
    }
    return client;
  }

}
