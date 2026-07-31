package com.mg_devjoint.library_management.dto.request.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for updating an existing book")
public record UpdateBookRequest(

        @Schema(
                description = "Title of the Book",
                example = "The Pragmatic Programmer",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Book title cannot be blank")
        @Size(max = 255, message = "Book title must be at most 255 characters")
        String title,

        @Schema(
                description = "ISBN of the Book",
                example = "978-0-13-469288-3",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "ISBN cannot be blank")
        @Size(max = 20, message = "ISBN must be at most 20 characters")
        String isbn,

        @Schema(
                description = "Description of the Book",
                example = "A classic handbook on software development and pragmatic programming."
        )
        @Size(max = 2000, message = "Book description must be at most 2000 characters")
        String description

) {
}
