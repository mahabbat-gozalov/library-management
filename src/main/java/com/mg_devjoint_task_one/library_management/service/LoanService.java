package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateLoanRequest;
import com.mg_devjoint_task_one.library_management.dto.response.LoanResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.model.Loan;

import java.util.UUID;

public interface LoanService {
    LoanResponse createLoan(CreateLoanRequest request);

    PageResponse<LoanResponse> getAllLoans(int page, int size);

    LoanResponse getLoanById(UUID loanId);

    LoanResponse returnBook(UUID loanId);

    Loan getLoanEntityById(UUID loanId);

}
