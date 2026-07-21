package com.mg_devjoint_task_one.library_management.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
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
        String description,

        @Schema(description = "IDs of the books currently associated with this category",
                example = """
                        ["febb76e0-4510-4270-b21d-b7d6ae0e1b18", "3ca7627f-e392-402f-b876-f66a308affdc"]
                        """)
        Set<UUID> categoryBookIdSet
) {
}
