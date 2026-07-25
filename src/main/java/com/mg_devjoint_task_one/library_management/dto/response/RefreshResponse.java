package com.mg_devjoint_task_one.library_management.dto.response;

public record RefreshResponse(
        String accessToken,
        String refreshToken
) {
}
