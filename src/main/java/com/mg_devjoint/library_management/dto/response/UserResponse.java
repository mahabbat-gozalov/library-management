package com.mg_devjoint.library_management.dto.response;

import com.mg_devjoint.library_management.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        UserRole role,
        String name,
        String surname,
        String phoneNumber,
        LocalDateTime createdAt
) {
}
