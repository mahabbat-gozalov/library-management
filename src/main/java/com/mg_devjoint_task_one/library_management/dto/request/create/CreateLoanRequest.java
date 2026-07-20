package com.mg_devjoint_task_one.library_management.dto.request.create;

import com.mg_devjoint_task_one.library_management.model.enums.LoanPeriod;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateLoanRequest(
        @NotNull(message = "Book ID cannot be null")
        UUID bookId,

        @NotNull(message = "Member ID cannot be null")
        UUID memberId,

        @NotNull(message = "Loan period cannot be null")
        LoanPeriod loanPeriod
) {
}