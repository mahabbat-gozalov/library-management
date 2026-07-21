package com.mg_devjoint_task_one.library_management.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response payload representing category details")
public record CategoryResponse(
        @Schema(description = "Unique identifier of the category",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(description = "Name of the category",
                example = "Science Fiction")
        String name,

        @Schema(description = "Description of the category",
                example = "Books involving futuristic science and technology")
        String description
) {
}
