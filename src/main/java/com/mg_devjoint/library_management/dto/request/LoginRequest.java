package com.mg_devjoint.library_management.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(
                description = "Email of the author",
                example = "mgzlovcontact@gmail.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @Schema(
                description = "Password of the author",
                example = "HTJlvQ74",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
