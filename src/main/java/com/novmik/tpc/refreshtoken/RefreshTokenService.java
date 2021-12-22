package com.novmik.tpc.refreshtoken;

import com.novmik.tpc.exception.TokenRefreshException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * RefreshToken business interface layer.
 */
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class RefreshTokenService {

  /**
   * {@link RefreshTokenRepository}.
   */
  private final RefreshTokenRepository rtRepository;

  /**
   * Продолжительность действия RefreshToken.
   * 2592000000мс = 30 дней
   */
  private final long durationMs;

  public RefreshTokenService(final RefreshTokenRepository rtRepository,
      @Value("${jwt.token.refresh.duration}") final Long durationMs) {
    this.rtRepository = rtRepository;
    this.durationMs = durationMs;
  }

  /**
   * Поиск RefreshToken по токену.
   *
   * @param token токен
   * @return {@link RefreshToken}
   */
  public Optional<RefreshToken> findRefreshTokenByToken(final String token) {
    return rtRepository.findRefreshTokenByToken(token);
  }

  /**
   * Сохранение RefreshToken.
   *
   * @param refreshToken {@link RefreshToken}
   * @return {@link RefreshToken}
   */
  public RefreshToken save(final RefreshToken refreshToken) {
    return rtRepository.save(refreshToken);
  }

  /**
   * Создание RefreshToken.
   *
   * @return {@link RefreshToken}
   */
  public RefreshToken createRefreshToken() {
    final RefreshToken refreshToken = new RefreshToken();
    refreshToken.setExpiryDate(Instant.now().plusMillis(durationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setRefreshCount(0L);
    return refreshToken;
  }

  /**
   * Проверка действительности токена.
   *
   * @param token {@link RefreshToken}
   * @throws TokenRefreshException если истёк срок действия
   */
  public void verifyExpiration(final RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      throw new TokenRefreshException(token.getToken(),
          "Токен не действителен. Отправьте новый запрос");
    }
  }

  /**
   * Удаление RefreshToken.
   *
   * @param refreshToken {@link RefreshToken}
   */
  public void deleteByRefreshToken(final String refreshToken) {
    rtRepository.deleteByToken(refreshToken);
  }

  /**
   * Увеличение счётчика обновления токена.
   *
   * @param refreshToken {@link RefreshToken}
   */
  public void increaseCount(final RefreshToken refreshToken) {
    refreshToken.incrementRefreshCount();
    save(refreshToken);
  }
}
