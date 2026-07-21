package com.mg_devjoint_task_one.library_management.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Response payload representing book details")
public record BookResponse(

        @Schema(
                description = "Unique identifier of the book",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Title of the book",
                example = "Clean Code"
        )
        String title,

        @Schema(
                description = "13-character ISBN number",
                example = "9780132350884"
        )
        String isbn,

        @Schema(
                description = "Detailed description or summary of the book",
                example = "A Handbook of Agile Software Craftsmanship"
        )
        String description,

        @Schema(
                description = "Total number of copies owned by the library",
                example = "10"
        )
        Integer fullQuantity,

        @Schema(
                description = "Number of copies currently available for loan",
                example = "7"
        )
        Integer availableQuantity,

        @Schema(
                description = "Set of author UUIDs associated with the book",
                example = """
                        ["febb76e0-4510-4270-b21d-b7d6ae0e1b18", "3ca7627f-e392-402f-b876-f66a308affdc"]
                        """
        )
        Set<UUID> authorIdSet,

        @Schema(
                description = "Set of category UUIDs associated with the book",
                example = """
                        ["febb76e0-4510-4270-b21d-b7d6ae0e1b18", "3ca7627f-e392-402f-b876-f66a308affdc"]
                        """
        )
        Set<UUID> categoryIdSet
) {
}