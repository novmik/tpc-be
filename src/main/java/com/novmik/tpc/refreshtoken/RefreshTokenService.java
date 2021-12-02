package com.novmik.tpc.refreshtoken;

import com.novmik.tpc.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.token.refresh.duration}")
    private Long refreshTokenDurationMs;

    public Optional<RefreshToken> findRefreshTokenByToken(final String token) {
        return refreshTokenRepository.findRefreshTokenByToken(token);
    }

    public RefreshToken save(final RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setRefreshCount(0L);
        return refreshToken;
    }

    public void verifyExpiration(final RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {

            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }

    public void deleteByRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    public void increaseCount(RefreshToken refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }
}
