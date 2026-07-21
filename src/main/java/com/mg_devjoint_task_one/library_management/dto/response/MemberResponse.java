package com.mg_devjoint_task_one.library_management.dto.response;

import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Response payload representing member details")
public record MemberResponse(

        @Schema(description = "Unique identifier of the member", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "First name of the member", example = "John")
        String firstName,

        @Schema(description = "Last name of the member", example = "Doe")
        String lastName,

        @Schema(description = "Email address of the member", example = "john.doe@example.com")
        String email,

        @Schema(description = "Phone number of the member", example = "+994 50 123 45 67")
        String phone,

        @Schema(description = "Current status of the member", example = "ACTIVE")
        MemberStatus status,

        @Schema(description = "Date the member was registered", example = "2026-01-15")
        LocalDate membershipDate
) {
}