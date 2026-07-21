package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateLoanRequest;
import com.mg_devjoint_task_one.library_management.dto.response.LoanResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.exception.InvalidOperationException;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.LoanMapper;
import com.mg_devjoint_task_one.library_management.model.*;
import com.mg_devjoint_task_one.library_management.model.enums.BookStatus;
import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;
import com.mg_devjoint_task_one.library_management.repository.LoanRepository;
import com.mg_devjoint_task_one.library_management.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;
    private final long memberLoansMaxLimit;

    public LoanServiceImpl(LoanRepository loanRepository,
                           BookService bookService,
                           MemberService memberService,
                           @Value("${member-loans-max-limit}") long memberLoansMaxLimit
    ) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.memberService = memberService;
        this.memberLoansMaxLimit = memberLoansMaxLimit;
    }

    @Override
    @Transactional
    public LoanResponse createLoan(CreateLoanRequest request) {
        Book book = bookService.getBookEntityById(request.bookId());

        Member member = memberService.getMemberEntityById(request.memberId());

        validateBookIsAvailableToLoan(book);

        validateMemberIsAllowedToLoan(member);

        Loan loan = Loan.create(book, member, request.loanPeriod());

        Loan savedLoan = loanRepository.save(loan);

        book.decreaseAvailableQuantity();

        return LoanMapper.toLoanResponse(savedLoan);
    }

    @Override
    public PageResponse<LoanResponse> getAllLoans(int page, int size) {
        Pageable pageable = getPageable(page, size);

        Page<Loan> allLoans = loanRepository.findAll(pageable);

        Page<LoanResponse> allLoanResponses = allLoans.map(LoanMapper::toLoanResponse);

        return PageResponse.of(allLoanResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponse getLoanById(UUID loanId) {
        Loan loanEntityById = getLoanEntityById(loanId);

        return LoanMapper.toLoanResponse(loanEntityById);
    }

    @Override
    @Transactional
    public LoanResponse returnBook(UUID loanId) {

        Loan loan = getLoanEntityById(loanId);

        if (loan.getReturnDate() != null) {
            throw new InvalidOperationException("Loan has already been returned.");
        }

        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();

        book.increaseAvailableQuantity();

        return LoanMapper.toLoanResponse(loan);
    }

    @Override
    public Loan getLoanEntityById(UUID loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found with id: " + loanId));
    }

    private void validateBookIsAvailableToLoan(Book book) {
        if (book.getStatus() != BookStatus.ACTIVE) {
            throw new InvalidOperationException("Only active books are allowed to loan. Current status: " + book.getStatus());
        }
        if (book.getAvailableQuantity() < 1) {
            throw new InvalidOperationException("The Book does not have enough available quantity.");
        }
    }

    private void validateMemberIsAllowedToLoan(Member member) {
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new InvalidOperationException("Only active members are allowed to loan. Current status: " + member.getStatus());
        }

        if (hasMemberOverdueLoans(member)) {
            throw new InvalidOperationException("The Member has overdue loans.");
        }

        if (hasMemberReachedLoansMaxLimit(member)) {
            throw new InvalidOperationException("The member has reached the maximum number of active loans.");
        }
    }

    private boolean hasMemberReachedLoansMaxLimit(Member member) {
        long countOfActiveLoans = loanRepository.countActiveLoansByMemberId(member.getId());

        return countOfActiveLoans >= memberLoansMaxLimit;
    }

    private boolean hasMemberOverdueLoans(Member member) {
        return loanRepository
                .existsOverdueLoanByMemberId(member.getId(), LocalDate.now());
    }

    private Pageable getPageable(int pageNumber, int pageSize) {

        pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        pageSize = pageSize <= 0 ? 10 : pageSize;

        return PageRequest.of(pageNumber - 1, pageSize);
    }
}
