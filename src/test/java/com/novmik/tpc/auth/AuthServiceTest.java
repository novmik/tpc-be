package com.novmik.tpc.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.ClientService;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.exception.UpdatePasswordException;
import com.novmik.tpc.refreshtoken.RefreshToken;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.security.JwtTokenProvider;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private ClientService clientService;
  @Mock
  private JwtTokenProvider tokenProvider;
  @Mock
  private RefreshTokenService rtService;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private AuthenticationManager authManager;
  private AuthService underTest;

  @BeforeEach
  void setUp() {
    underTest =
        new AuthService(clientService, tokenProvider, rtService, passwordEncoder, authManager);
  }

  @Test
  void canAuthenticateUser() {
    LoginRequest loginRequest = new LoginRequest();
    Authentication auth = new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword());
    when(authManager.authenticate(auth)).thenReturn(auth);
    assertThat(underTest.authenticateUser(loginRequest)).isPresent();
  }

  @Test
  void notCanAuthenticateUser() {
    LoginRequest loginRequest = new LoginRequest();
    Authentication auth = new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword());
    when(authManager.authenticate(auth)).thenReturn(null);
    assertThat(underTest.authenticateUser(loginRequest)).isEmpty();
  }

  @Test
  void canUpdatePassword() {
    Client currentUser = new Client("test@test.com", "encodedPass", "T", "E", true, true);
    CustomUserDetails customUserDetails = new CustomUserDetails(currentUser);
    UpdatePasswordRequest updatePassRequest = new UpdatePasswordRequest("oldPass", "newPass");
    String email = customUserDetails.getUsername();
    when(clientService.getClientByEmail(email)).thenReturn(Optional.of(currentUser));
    when(passwordEncoder.matches(
        updatePassRequest.getOldPassword(), currentUser.getPassword())).thenReturn(true);
    assertThat(underTest.updatePassword(customUserDetails, updatePassRequest)).isPresent();
  }

  @Test
  void willThrowWhenUpdatePassword() {
    Client currentClient = new Client("test@test.com", "encodedPass", "T", "E", true, true);
    CustomUserDetails customUserDetails = new CustomUserDetails(currentClient);
    UpdatePasswordRequest updatePassRequest = new UpdatePasswordRequest("oldPass", "newPass");
    String email = customUserDetails.getUsername();
    when(clientService.getClientByEmail(email)).thenReturn(Optional.of(currentClient));
    when(passwordEncoder.matches(
        updatePassRequest.getOldPassword(), currentClient.getPassword())).thenReturn(false);
    assertThatThrownBy(() -> underTest.updatePassword(customUserDetails, updatePassRequest))
        .isInstanceOf(UpdatePasswordException.class)
        .hasMessage(String.format(
            "Не удалось обновить пароль для [%s]: [Неверный пароль]", currentClient.getEmail()));
  }

  @Test
  void canGenerateToken() {
    Client currentClient = new Client("test@test.com", "encodedPass", "T", "E", true, true);
    CustomUserDetails customUserDetails = new CustomUserDetails(currentClient);
    underTest.generateToken(customUserDetails);
    verify(tokenProvider).generateToken(customUserDetails);
  }

  @Test
  void createAndPersistRefreshToken() {
    RefreshToken refreshToken = new RefreshToken();
    when(rtService.createRefreshToken()).thenReturn(refreshToken);
    LoginRequest loginRequest = new LoginRequest();
    Authentication auth = new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword());
    underTest.createAndPersistRefreshToken(auth);
    ArgumentCaptor<RefreshToken> rtArgumentCaptor = ArgumentCaptor.forClass(RefreshToken.class);
    verify(rtService).save(rtArgumentCaptor.capture());
    RefreshToken capturedRt = rtArgumentCaptor.getValue();
    assertThat(capturedRt).isEqualTo(refreshToken);
  }

  @Test
  void refreshJwtToken() {
  }
}