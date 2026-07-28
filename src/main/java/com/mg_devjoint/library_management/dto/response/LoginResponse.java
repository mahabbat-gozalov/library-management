package com.mg_devjoint.library_management.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
