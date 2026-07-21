package com.mg_devjoint_task_one.library_management.dto.request.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request body for updating an existing author")
public record UpdateAuthorRequest(

        @Schema(
                description = "First name of the author",
                example = "Robert",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "First Name cannot be blank")
        @Size(max = 100, message = "First Name must be at most 100 characters")
        String firstName,

        @Schema(
                description = "Last name of the author",
                example = "Martin",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Last Name cannot be blank")
        @Size(max = 100, message = "Last Name must be at most 100 characters")
        String lastName,

        @Schema(
                description = "Short biography or summary of the author",
                example = "Software engineer and author of Clean Code."
        )
        @Size(max = 500, message = "Summary must be at most 500 characters")
        String summary,

        @Schema(
                description = "Email address of the author",
                example = "unclebob@cleancoder.com"
        )
        @Size(max = 255, message = "Email must be at most 255 characters")
        @Email(message = "Email format is not valid")
        String email

) {
}