package com.mg_devjoint_task_one.library_management.dto.response;

import com.mg_devjoint_task_one.library_management.dto.enums.LoanStatus;

import java.time.LocalDate;
import java.util.UUID;

public record LoanResponse(
        UUID loanId,
        UUID bookId,
        UUID memberId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        LoanStatus loanStatus

) {
}
