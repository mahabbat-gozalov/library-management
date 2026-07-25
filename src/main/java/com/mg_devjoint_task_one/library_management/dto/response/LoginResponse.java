package com.mg_devjoint_task_one.library_management.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
