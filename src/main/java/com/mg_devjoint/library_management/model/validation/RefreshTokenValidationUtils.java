package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.User;

import java.time.LocalDateTime;

public final class RefreshTokenValidationUtils {
    private RefreshTokenValidationUtils() {
    }

    public static void validateTokenValue(String refreshTokenValue) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            throw new InvalidEntityDataException("Refresh token value cannot be null or blank");
        }
    }

    public static void validateCreatedAtAndExpiresAt(LocalDateTime createdAt, LocalDateTime expiresAt) {
        if (createdAt == null) {
            throw new InvalidEntityDataException("Refresh token created time value cannot be null");
        }
        if (expiresAt == null) {
            throw new InvalidEntityDataException("Refresh token expiration time value cannot be null");
        }
        if (createdAt.isAfter(expiresAt)) {
            throw new InvalidEntityDataException("Refresh token creation time cannot be after expiration time");
        }
    }

    public static void validateAssociatedUser(User user) {
        if (user == null) {
            throw new InvalidEntityDataException("User object cannot be null");
        }
    }


}
