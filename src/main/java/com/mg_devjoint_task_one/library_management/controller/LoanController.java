package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateLoanRequest;
import com.mg_devjoint_task_one.library_management.dto.response.LoanResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Loans", description = "Endpoints for creating, viewing, and returning book loans")
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Create a new loan",
            description = "Creates a loan for the given book and member, decreasing the book's available quantity. " +
                    "The book must be ACTIVE with at least one available copy, and the member must be ACTIVE, " +
                    "have no overdue loans, and not have reached the maximum number of active loans."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Validation error, or the book/member is not eligible for a loan (book not ACTIVE,
                            no available copies, member not ACTIVE, member has overdue loans, or member has reached
                            the maximum active loan limit)
                            """),
            @ApiResponse(responseCode = "404", description = "Book or member not found")
    })
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
        LoanResponse response = loanService.createLoan(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all loans",
            description = "Returns a paginated list of loans. Page numbering starts at 1."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponse<LoanResponse>> getAllLoans(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size
    ) {
        var body = loanService.getAllLoans(page, size);
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get a loan by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan found"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable UUID loanId) {
        LoanResponse response = loanService.getLoanById(loanId);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Return a loaned book",
            description = "Marks the loan as returned (sets the return date to today) and increases " +
                    "the book's available quantity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "400", description = "Loan has already been returned")
    })
    @PatchMapping("/{loanId}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable UUID loanId) {
        LoanResponse response = loanService.returnBook(loanId);

        return ResponseEntity.ok(response);
    }

}