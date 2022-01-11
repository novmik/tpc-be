package com.novmik.tpc.refreshtoken;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.client.Client;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

  Client client;
  RefreshToken underTest;
  Instant instant;

  @BeforeEach
  void setUp() {
    client = new Client("test@test.com", "password", "T", "E", true, true);
    instant = Instant.now();
    underTest = new RefreshToken(230L, "token", client, 0L, instant);
  }

  @Test
  void incrementRefreshCount() {
    underTest.incrementRefreshCount();
    assertThat(underTest.getRefreshCount()).isEqualTo(1);
  }

  @Test
  void getIdRefreshToken() {
    assertThat(underTest.getIdRefreshToken()).isEqualTo(230L);
  }

  @Test
  void getToken() {
    assertThat(underTest.getToken()).isEqualTo("token");
  }

  @Test
  void getClient() {
    assertThat(underTest.getClient()).isEqualTo(client);
  }

  @Test
  void getRefreshCount() {
    assertThat(underTest.getRefreshCount()).isEqualTo(0);
  }

  @Test
  void getExpiryDate() {
    assertThat(underTest.getExpiryDate()).isEqualTo(instant);
  }

  @Test
  void setIdRefreshToken() {
    underTest.setIdRefreshToken(290L);
    assertThat(underTest.getIdRefreshToken()).isEqualTo(290L);
  }

  @Test
  void setToken() {
    underTest.setToken("TestToken");
    assertThat(underTest.getToken()).isEqualTo("TestToken");
  }

  @Test
  void setClient() {
    Client test19 = new Client();
    underTest.setClient(test19);
    assertThat(underTest.getClient()).isEqualTo(test19);
  }

  @Test
  void setRefreshCount() {
    underTest.setRefreshCount(100L);
    assertThat(underTest.getRefreshCount()).isEqualTo(100L);
  }

  @Test
  void setExpiryDate() {
    Instant testInstant = Instant.now();
    underTest.setExpiryDate(testInstant);
    assertThat(underTest.getExpiryDate()).isEqualTo(testInstant);
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("RefreshToken(idRefreshToken=230, token=token, refreshCount=0, expiryDate=" + instant + ")");
  }
}