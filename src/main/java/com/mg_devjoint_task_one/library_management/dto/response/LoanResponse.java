package com.mg_devjoint_task_one.library_management.dto.response;

import com.mg_devjoint_task_one.library_management.dto.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Response payload containing loan details")
public record LoanResponse(
        @Schema(description = "Unique identifier of the loan",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID loanId,

        @Schema(description = "Unique identifier of the book",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID bookId,

        @Schema(description = "Unique identifier of the member",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID memberId,

        @Schema(description = "Date when the book was borrowed",
                example = "2026-07-21")
        LocalDate loanDate,

        @Schema(description = "Due date by which the book must be returned",
                example = "2026-08-04")
        LocalDate dueDate,

        @Schema(description = "Actual date when the book was returned (null if not returned yet)",
                example = "2026-07-28")
        LocalDate returnDate,

        @Schema(description = "Current status of the loan (e.g., ACTIVE, RETURNED, OVERDUE)",
                example = "ACTIVE")
        LoanStatus loanStatus
) {
}