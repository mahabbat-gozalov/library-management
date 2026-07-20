package com.mg_devjoint_task_one.library_management.dto.request.update;

import jakarta.validation.constraints.*;

public record UpdateMemberRequest(

        @NotBlank(message = "First Name cannot be blank")
        @Size(max = 100, message = "First Name must be at most 100 characters")
        String firstName,

        @NotBlank(message = "Last Name cannot be blank")
        @Size(max = 100, message = "Last Name must be at most 100 characters")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Size(max = 255, message = "Email must be at most 255 characters")
        @Email(message = "Email format is not valid")
        String email,

        @NotBlank(message = "Phone cannot be blank")
        @Size(max = 20, message = "Phone must be at most 20 characters")
        @Pattern(
                regexp = "^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$",
                message = "Phone number must be in the format: +994 XX XXX XX XX"
        )
        String phone

) {
}
