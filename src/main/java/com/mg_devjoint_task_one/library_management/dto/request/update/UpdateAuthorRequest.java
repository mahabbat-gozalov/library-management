package com.mg_devjoint_task_one.library_management.dto.request.update;

import jakarta.validation.constraints.*;

public record UpdateAuthorRequest(

        @NotBlank(message = "First Name cannot be blank")
        @Size(max = 100, message = "First Name must be at most 100 characters")
        String firstName,

        @NotBlank(message = "Last Name cannot be blank")
        @Size(max = 100, message = "Last Name must be at most 100 characters")
        String lastName,

        @Size(max = 500, message = "Summary must be at most 500 characters")
        String summary,

        @Size(max = 255, message = "Email must be at most 255 characters")
        @Email(message = "Email format is not correct")
        String email

) {
}
