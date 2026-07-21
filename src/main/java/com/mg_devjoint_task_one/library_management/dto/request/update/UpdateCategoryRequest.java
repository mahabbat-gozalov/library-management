package com.mg_devjoint_task_one.library_management.dto.request.update;

import com.mg_devjoint_task_one.library_management.dto.enums.CollectionUpdateMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Payload used to update an existing category")
public record UpdateCategoryRequest(
        @Schema(description = "Name of the category, must be unique",
                example = "Science Fiction",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Category name cannot be blank")
        @Size(max = 100, message = "Category name must be at most 100 characters")
        String name,

        @Schema(description = "Optional description of the category",
                example = "Books involving futuristic science and technology",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 500, message = "Category description must be at most 500 characters")
        String description,

        @Schema(
                description = """
                        Optional list of books IDs. Can be omitted or empty, but if provided, elements cannot be null.
                        """,
                example = """
                        ["febb76e0-4510-4270-b21d-b7d6ae0e1b18", "3ca7627f-e392-402f-b876-f66a308affdc"]
                        """,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Set<@NotNull(message = "Book ID cannot be null") UUID> bookIdSet,

        @Schema(
                description = "Defines whether to add to or replace the existing set of books",
                example = "REPLACE",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        CollectionUpdateMode bookSetUpdateMode

) {
}