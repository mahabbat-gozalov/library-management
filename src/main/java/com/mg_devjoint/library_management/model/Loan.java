package com.mg_devjoint.library_management.model;

import com.mg_devjoint.library_management.dto.enums.LoanPeriod;
import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.validation.CommonValidationUtils;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.validateIdCannotBeNull;
import static com.mg_devjoint.library_management.model.validation.LoanValidationUtils.*;

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

        validateAssociateBook(book);
        validateAssociateMember(member);
        validateLoanPeriod(loanPeriod);

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(loanPeriod.getDays());

        Loan loan = new Loan();

        loan.book = book;
        loan.member = member;
        loan.loanDate = loanDate;
        loan.dueDate = dueDate;

        return loan;
    }


    public static Loan createWithId(UUID id, Book book, Member member, LoanPeriod loanPeriod) {
        validateIdCannotBeNull(id);

        Loan loan = create(book, member, loanPeriod);

        loan.id = id;

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