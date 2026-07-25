package com.mg_devjoint.library_management.dto.response;

public record RefreshResponse(
        String accessToken,
        String refreshToken
) {
}
