package com.mg_devjoint.library_management.dto.response;

import java.util.UUID;

public record CreateUserResponse(
        UUID userId,
        String email,
        String name,
        String surname,
        String phoneNumber,
        String role,
        boolean temporaryPasswordEmailSent
) {
    public CreateUserResponse withTemporaryPasswordEmailSent(boolean sent) {
        return new CreateUserResponse(userId,
                email,
                name,
                surname,
                phoneNumber,
                role,
                sent
        );
    }

}
