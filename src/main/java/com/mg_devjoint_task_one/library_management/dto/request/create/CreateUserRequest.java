package com.mg_devjoint_task_one.library_management.dto.request.create;

import com.mg_devjoint.library_management.model.enums.UserRole;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email must be at most 255 characters")
        String email,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        @Size(max = 100, message = "Surname must be at most 100 characters")
        String surname,

        @NotBlank(message = "Phone number cannot be blank")
        @Size(max = 20, message = "Phone number must be at most 20 characters")
        @Pattern(
                regexp = "^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$",
                message = "Phone number must be in the format: +994 XX XXX XX XX"
        )
        String phoneNumber,

        @NotNull(message = "Role cannot be null")
        UserRole role
) {
}