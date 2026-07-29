package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.dto.enums.LoanPeriod;
import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.*;

public final class LoanValidationUtils {
    private LoanValidationUtils() {
    }

    public static void validateAssociateBook(Book book) {
        if (book == null) {
            throw new InvalidEntityDataException("Book cannot be null");
        }
    }

    public static void validateAssociateMember(Member member) {
        if (member == null) {
            throw new InvalidEntityDataException("Member cannot be null");
        }
    }

    public static void validateLoanPeriod(LoanPeriod loanPeriod) {
        if (loanPeriod == null) {
            throw new InvalidEntityDataException("loanPeriod cannot be null");
        }
    }

}
