package com.mg_devjoint_task_one.library_management.mapper;

import com.mg_devjoint_task_one.library_management.dto.enums.LoanStatus;
import com.mg_devjoint_task_one.library_management.dto.response.LoanResponse;
import com.mg_devjoint_task_one.library_management.model.Loan;

import java.time.LocalDate;

public final class LoanMapper {
    private LoanMapper() {
    }

    public static LoanResponse toLoanResponse(Loan loan) {

        LoanStatus loanStatus;

        boolean isLoanOverdue = loan.getDueDate().isBefore(LocalDate.now());

        if (loan.getReturnDate() != null) {
            loanStatus = LoanStatus.RETURNED;
        } else if (isLoanOverdue) {
            loanStatus = LoanStatus.OVERDUE;
        } else {
            loanStatus = LoanStatus.ACTIVE;
        }


        return new LoanResponse(
                loan.getId(),
                loan.getBook().getId(),
                loan.getMember().getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loanStatus

        );
    }
}
