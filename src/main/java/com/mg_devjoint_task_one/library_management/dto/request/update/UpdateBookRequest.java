package com.mg_devjoint_task_one.library_management.dto.request.update;

import com.mg_devjoint_task_one.library_management.dto.enums.CollectionUpdateMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;
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
        String description,

        @Schema(
                description = "Total quantity of the book in the library",
                example = "20",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Full quantity cannot be null")
        @PositiveOrZero(message = "Full quantity must be at least 0")
        Integer fullQuantity,

        @Schema(
                description = """
                        Optional list of author IDs. Can be omitted or empty, but if provided, elements cannot be null.
                        """,
                example = """
                        ["6e92483e-cd2d-4089-b8f4-a0aead9ae2b5", "de68490c-c2ed-4325-9358-dba35a36f475"]
                        """,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Set<@NotNull(message = "Author ID cannot be null") UUID> authorIdSet,

        @Schema(
                description = """
                        Optional list of category IDs. Can be omitted or empty, but if provided, elements cannot be null.
                        """,
                example = """
                        ["febb76e0-4510-4270-b21d-b7d6ae0e1b18", "3ca7627f-e392-402f-b876-f66a308affdc"]
                        """,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Set<@NotNull(message = "Category ID cannot be null") UUID> categoryIdSet,

        @Schema(
                description = "Defines whether to add to or replace the existing set of authors",
                example = "REPLACE"
        )
        CollectionUpdateMode authorSetUpdateMode,

        @Schema(
                description = "Defines whether to add to or replace the existing set of categories",
                example = "ADD"
        )
        CollectionUpdateMode categorySetUpdateMode
) {
}
