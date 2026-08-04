package com.mg_devjoint.library_management.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BookSummaryResponse(

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
        Integer availableQuantity

) {
}

