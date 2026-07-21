package com.mg_devjoint_task_one.library_management.dto.request.create;

import com.mg_devjoint_task_one.library_management.dto.enums.LoanPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request body for creating a new loan")
public record CreateLoanRequest(

        @Schema(description = "Unique identifier of the book",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Book ID cannot be null")
        UUID bookId,

        @Schema(description = "Unique identifier of the member",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Member ID cannot be null")
        UUID memberId,

        @Schema(description = "Loan duration policy (SHORT: 7 days, STANDARD: 14 days, EXTENDED: 21 days)",
                example = "STANDARD",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Loan period cannot be null")
        LoanPeriod loanPeriod
) {
}