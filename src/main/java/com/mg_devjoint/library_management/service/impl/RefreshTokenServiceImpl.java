package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.exception.InvalidTokenException;
import com.mg_devjoint.library_management.exception.NotFoundException;
import com.mg_devjoint.library_management.model.RefreshToken;
import com.mg_devjoint.library_management.model.User;
import com.mg_devjoint.library_management.repository.RefreshTokenRepository;
import com.mg_devjoint.library_management.service.RefreshTokenService;
import com.mg_devjoint.library_management.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${security.refresh-token.expiration-milliseconds}")
    private long expirationMilliseconds;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }


    @Override
    public String createRefreshToken(UUID userId) {

        User user = userService.findUserById(userId);

        String refreshTokenValue = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiryDate = now.plus(Duration.ofMillis(expirationMilliseconds));

        RefreshToken refreshTokenEntity =  RefreshToken.create(refreshTokenValue, user, expiryDate, now);

        refreshTokenRepository.save(refreshTokenEntity);

        return refreshTokenValue;
    }


    @Override
    public void revokeRefreshToken(RefreshToken refreshToken) {
        refreshToken.revoke();

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public int revokeAllRefreshTokensByUser(UUID userId) {
     return    refreshTokenRepository.revokeAllByUserId(userId);
    }

    @Override
    public RefreshToken getRefreshTokenByValue(String refreshToken) {

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));
    }

    @Override
    public boolean isRefreshTokenValid(RefreshToken refreshToken) {
        return !refreshToken.isRevoked() && !refreshToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public void validateRefreshToken(RefreshToken refreshToken) {
        boolean isRefreshTokenValid = isRefreshTokenValid(refreshToken);

        if (!isRefreshTokenValid) {
            throw new InvalidTokenException("Invalid refresh token");
        }
    }


}
