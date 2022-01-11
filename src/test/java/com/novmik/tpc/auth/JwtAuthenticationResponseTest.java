package com.novmik.tpc.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtAuthenticationResponseTest {

  JwtAuthenticationResponse underTest;

  @BeforeEach
  void setUp() {
    underTest = new JwtAuthenticationResponse("accessToken", "refreshToken");
  }

  @Test
  void getAccessToken() {
    assertThat(underTest.getAccessToken()).isEqualTo("accessToken");
  }

  @Test
  void getRefreshToken() {
    assertThat(underTest.getRefreshToken()).isEqualTo("refreshToken");
  }

  @Test
  void setAccessToken() {
    underTest.setAccessToken("Set Test accessToken");
    assertThat(underTest.getAccessToken()).isEqualTo("Set Test accessToken");
  }

  @Test
  void setRefreshToken() {
    underTest.setRefreshToken("Set Test refreshToken");
    assertThat(underTest.getRefreshToken()).isEqualTo("Set Test refreshToken");
  }

  @Test
  void testEquals() {
    JwtAuthenticationResponse test1 = new JwtAuthenticationResponse("accessToken", "refreshToken");
    assertThat(underTest.equals(test1)).isTrue();
  }

  @Test
  void canEqual() {
    JwtAuthenticationResponse test1 = underTest;
    assertThat(underTest.canEqual(test1)).isTrue();
  }

  @Test
  void testHashCode() {
    JwtAuthenticationResponse test11 = new JwtAuthenticationResponse("accessToken", "refreshToken");
    assertThat(underTest.hashCode()).isEqualTo(test11.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "JwtAuthenticationResponse(accessToken=accessToken, refreshToken=refreshToken)");
  }
}