package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateLoanRequest;
import com.mg_devjoint_task_one.library_management.dto.response.LoanResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
        LoanResponse response = loanService.createLoan(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<PageResponse<LoanResponse>> getAllLoans(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size

    ) {
        var body = loanService.getAllLoans(page, size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable UUID loanId) {
        LoanResponse response = loanService.getLoanById(loanId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{loanId}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable UUID loanId) {
        LoanResponse response = loanService.returnBook(loanId);

        return ResponseEntity.ok(response);
    }

}
