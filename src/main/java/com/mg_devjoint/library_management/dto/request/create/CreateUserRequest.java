package com.mg_devjoint.library_management.dto.request.create;

import com.mg_devjoint.library_management.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @Schema(
                description = "Email of the user",
                example = "mgzlovcontact@gmail.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email must be at most 255 characters")
        String email,

        @Schema(
                description = "First name of the author",
                example = "Robert",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @Schema(
                description = "Last name of the author",
                example = "Baratheon",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Surname cannot be blank")
        @Size(max = 100, message = "Surname must be at most 100 characters")
        String surname,

        @Schema(description = "Azerbaijani phone number in the format: +994 XX XXX XX XX (XX must be a valid operator code)",
                example = "+994 50 123 45 67",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Phone number cannot be blank")
        @Size(max = 20, message = "Phone number must be at most 20 characters")
        @Pattern(
                regexp = "^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$",
                message = "Phone number must be in the format: +994 XX XXX XX XX"
        )
        String phoneNumber,

        @Schema(description = "Role of the user",
                example = "ROLE_LIBRARIAN",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Role cannot be null")
        UserRole role
) {
}