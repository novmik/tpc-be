package com.novmik.tpc.auth;

import static com.novmik.tpc.exception.ExceptionConstant.ACCOUNT_DISABLED;
import static com.novmik.tpc.exception.ExceptionConstant.ACCOUNT_LOCKED;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

  private final ClientService clientService;
  private final JwtTokenProvider tokenProvider;
  private final RefreshTokenService refreshTokenService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public Optional<Authentication> authenticateUser(final LoginRequest loginRequest) {
    return Optional.ofNullable(authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword())));
  }

  private Boolean currentPasswordMatches(final Client currentUser,
      final String password) {
    return passwordEncoder.matches(password, currentUser.getPassword());
  }

  public Optional<Client> updatePassword(final CustomUserDetails customUserDetails,
      final UpdatePasswordRequest updatePasswordRequest) {
    String email = customUserDetails.getUsername();
    Client currentUser = clientService.getClient(email)
        .orElseThrow();

    if (!currentPasswordMatches(currentUser, updatePasswordRequest.getOldPassword())) {
      log.info("Current password is invalid for [" + currentUser.getPassword() + "]");
      throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
    }
    String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
    currentUser.setPassword(newPassword);
    clientService.saveClient(currentUser);
    return Optional.of(currentUser);
  }

  public String generateToken(final CustomUserDetails customUserDetails) {
    return tokenProvider.generateToken(customUserDetails);
  }

  public Optional<RefreshToken> createAndPersistRefreshToken(final Authentication authentication) {
    Client currentUser = (Client) authentication.getPrincipal();
    RefreshToken refreshToken = refreshTokenService.createRefreshToken();
    refreshToken.setClient(currentUser);
    refreshToken = refreshTokenService.save(refreshToken);
    return Optional.ofNullable(refreshToken);
  }

  public Optional<String> refreshJwtToken(final TokenRefreshRequest tokenRefreshRequest) {
    String strRefreshToken = tokenRefreshRequest.getRefreshToken();

    return Optional.of(refreshTokenService.findRefreshTokenByToken(strRefreshToken)
            .map(refreshToken -> {
              refreshTokenService.verifyExpiration(refreshToken);
              refreshTokenService.increaseCount(refreshToken);
              return refreshToken;
            })
            .map(RefreshToken::getClient)
            .map(this::checkClientAccess)
            .map(CustomUserDetails::new)
            .map(this::generateToken))
        .orElseThrow(() -> new TokenRefreshException(strRefreshToken,
            "Отсутствует refresh token в ДБ. Пожалуйста, перезайдите."));
  }

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
