package com.mg_devjoint.library_management.dto.request.update;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(

        @NotBlank(message = "Old password must be provided")
        String oldPassword,

        @NotBlank(message = "New password must be provided")
        String newPassword
) {
}
