package com.novmik.tpc.refreshtoken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import com.novmik.tpc.client.Client;
import com.novmik.tpc.exception.TokenRefreshException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

  private static final long durationMs = 2592000000L;

  @Mock
  private RefreshTokenRepository rtRepository;
  private RefreshTokenService underTest;

  @BeforeEach
  void setUp() {
    underTest = new RefreshTokenService(rtRepository, durationMs);
  }

  @Test
  void canFindRefreshTokenByToken() {
    String token = "test Token";
    underTest.findRefreshTokenByToken(token);
    verify(rtRepository).findRefreshTokenByToken(token);
  }

  @Test
  void canSave() {
    RefreshToken refreshToken = new RefreshToken();
    underTest.save(refreshToken);
    verify(rtRepository).save(refreshToken);
  }

  @Test
  void canCreateRefreshToken() {
    assertThat(underTest.createRefreshToken()).isNotNull();
  }

  @Test
  void canVerifyExpiration() {
    RefreshToken token = new RefreshToken(
        100L,
        "Test Token",
        new Client(),
        0L,
        Instant.now().plusSeconds(100));
    underTest.verifyExpiration(token);
  }

  @Test
  void canNotVerifyExpiration() {
    RefreshToken token = new RefreshToken(
        100L,
        "Test Token",
        new Client(),
        0L,
        Instant.now().minusSeconds(100));
    assertThatThrownBy(() -> underTest.verifyExpiration(token))
        .isInstanceOf(TokenRefreshException.class)
        .hasMessage(
            String.format(
                "Не обновился токен [%s]: [Токен не действителен. Отправьте новый запрос]",
            token.getToken()));
  }

  @Test
  void canDeleteByRefreshToken() {
    String refreshToken = "Test RefreshToken";
    underTest.deleteByRefreshToken(refreshToken);
    verify(rtRepository).deleteByToken(refreshToken);
  }

  @Test
  void canIncreaseCount() {
    RefreshToken token = new RefreshToken(
        100L,
        "Test Token",
        new Client(),
        0L,
        Instant.now().plusSeconds(100));
    underTest.increaseCount(token);
    verify(rtRepository).save(token);
  }
}