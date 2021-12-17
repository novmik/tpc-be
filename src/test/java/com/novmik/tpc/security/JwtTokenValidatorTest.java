package com.novmik.tpc.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.exception.InvalidTokenRequestException;
import com.novmik.tpc.role.Role;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtTokenValidatorTest {

  private static final String jwtSecret = "testSecret";
  private static final long jwtExpiryInMs = 2500;

  @Mock
  private JwtTokenProvider tokenProvider;
  private JwtTokenValidator underTest;

  @BeforeEach
  void setUp() {
    this.tokenProvider = new JwtTokenProvider(jwtSecret, jwtExpiryInMs);
    this.underTest = new JwtTokenValidator(jwtSecret);
  }

  @Test
  void canValidateToken() {
    String token = tokenProvider.generateToken(stubCustomClient());
    assertThat(underTest.validateToken(token)).isTrue();
  }

  @Test
  void willThrowWhenTokenIsExpired() throws InterruptedException {
    String token = tokenProvider.generateToken(stubCustomClient());
    TimeUnit.MILLISECONDS.sleep(jwtExpiryInMs);
    assertThatThrownBy(() -> underTest.validateToken(token))
        .isInstanceOf(InvalidTokenRequestException.class)
        .hasMessageContaining("Срок действия токена истёк. Требуется обновление");
  }

  @Test
  void willThrowWhenTokenIsDamaged() {
    String token = tokenProvider.generateToken(stubCustomClient());
    assertThatThrownBy(() -> underTest.validateToken(token + "-Damage"))
        .isInstanceOf(InvalidTokenRequestException.class)
        .hasMessageContaining("Неверный ключ: [JWT] token: ");
  }

  private CustomUserDetails stubCustomClient() {
    Client client = new Client();
    client.setEmail("test@test.com");
    client.setRoles(Collections.singleton(
        new Role(50L, "Role_ADMIN", Collections.emptyList())));
    return new CustomUserDetails(client);
  }
}