package com.novmik.tpc.refreshtoken;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findRefreshTokenByToken(String token);

  void deleteByToken(String token);
}
