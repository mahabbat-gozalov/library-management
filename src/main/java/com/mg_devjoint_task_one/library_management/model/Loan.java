package com.mg_devjoint_task_one.library_management.model;

import com.mg_devjoint_task_one.library_management.model.enums.LoanPeriod;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "LOANS")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    protected Loan() {
    }


    public static Loan create(Book book, Member member, LoanPeriod loanPeriod) {

        if (book == null || member == null || loanPeriod == null) throw new IllegalArgumentException("null arguments is not allowed");

        LocalDate loanDate = LocalDate.now();

        Loan loan = new Loan();

        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(loanDate);
        loan.setDueDate(loanDate.plusDays(loanPeriod.getDays()));

        return loan;
    }

    public UUID getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan loan)) return false;
        return id != null && id.equals(loan.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}