package com.novmik.tpc.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.role.Role;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

  private static final String jwtSecret = "testSecret";
  private static final long jwtExpiryInMs = 25000;

  @Mock
  private JwtTokenProvider underTest;

  @BeforeEach
  void setUp() {
    underTest = new JwtTokenProvider(jwtSecret, jwtExpiryInMs);
  }

  @Test
  void canGenerateToken() {
    String token = underTest.generateToken(stubCustomClient());
    assertThat(token).isNotNull();
  }

  @Test
  void canGetSubjectFromJwt() {
    String token = underTest.generateToken(stubCustomClient());
    assertThat(underTest.getSubjectFromJwt(token)).isEqualTo("test@test.com");
  }

  @Test
  void canGetPermissionsFromJwt() {
    String token = underTest.generateToken(stubCustomClient());
    assertThat(underTest.getPermissionsFromJwt(token)).isNotNull();
  }

  private CustomUserDetails stubCustomClient() {
    Client client = new Client();
    client.setEmail("test@test.com");
    client.setRoles(Collections.singleton(
        new Role(50L, "Role_ADMIN", Collections.emptyList())));
    return new CustomUserDetails(client);
  }
}