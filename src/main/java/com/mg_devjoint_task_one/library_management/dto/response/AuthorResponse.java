package com.mg_devjoint_task_one.library_management.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response payload representing author details")
public record AuthorResponse(

        @Schema(
                description = "Unique identifier of the author",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "First name of the author",
                example = "Robert"
        )
        String firstName,

        @Schema(
                description = "Last name of the author",
                example = "Martin"
        )
        String lastName,

        @Schema(
                description = "Short biography or summary of the author",
                example = "Software engineer and author of Clean Code."
        )
        String summary,

        @Schema(
                description = "Email address of the author",
                example = "unclebob@cleancoder.com"
        )
        String email
) {
}