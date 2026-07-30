package com.mg_devjoint.library_management.model.validation;

import java.time.LocalDate;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.enums.MemberStatus;

public final class MemberValidationUtils {
    private MemberValidationUtils() {
    }

    public static void validateMemberStatus(final MemberStatus memberStatus) {
        if (memberStatus == null) {
            throw new InvalidEntityDataException("member status cannot be null");
        }
    }

    public static void validateMembershipDate(final LocalDate membershipDate) {
        if (membershipDate == null) {
            throw new InvalidEntityDataException("membership date cannot be null");
        }
    }

}

