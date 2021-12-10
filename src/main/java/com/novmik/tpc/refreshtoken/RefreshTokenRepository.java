package com.novmik.tpc.refreshtoken;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RefreshToken data persistence layer operation interface.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  /**
   * Поиск RefreshToken по токену.
   *
   * @param token токен
   * @return RefreshToken {@link RefreshToken}
   */
  Optional<RefreshToken> findRefreshTokenByToken(String token);

  /**
   * Удаление RefreshToken по токену.
   *
   * @param token токен
   */
  void deleteByToken(String token);
}
