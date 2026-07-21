package com.mg_devjoint_task_one.library_management.dto.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Payload used to create a new category")
public record CreateCategoryRequest(
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
        String description
) {
}
