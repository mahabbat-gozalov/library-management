package com.mg_devjoint_task_one.library_management.dto.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Payload used to create a new member")
public record CreateMemberRequest(

        @Schema(description = "First name of the member",
                example = "John",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First Name cannot be blank")
        @Size(max = 100, message = "First Name must be at most 100 characters")
        String firstName,

        @Schema(description = "Last name of the member",
                example = "Doe",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Last Name cannot be blank")
        @Size(max = 100, message = "Last Name must be at most 100 characters")
        String lastName,

        @Schema(description = "Email address of the member, must be a valid email format",
                example = "john.doe@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email cannot be blank")
        @Size(max = 255, message = "Email must be at most 255 characters")
        @Email(message = "Email format is not valid")
        String email,

        @Schema(description = "Azerbaijani phone number in the format: +994 XX XXX XX XX (XX must be a valid operator code)",
                example = "+994 50 123 45 67",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Phone cannot be blank")
        @Size(max = 20, message = "Phone must be at most 20 characters")
        @Pattern(
                regexp = "^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$",
                message = "Phone number must be in the format: +994 XX XXX XX XX"
        )
        String phone
) {
}