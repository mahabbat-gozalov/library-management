package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;

public final class AuthorValidationUtils {
    private AuthorValidationUtils() {
    }

    public static void validateSummary(String summary) {
        if (summary == null) return;
        if (summary.length() > 500) {
            throw new InvalidEntityDataException("Summary contains more than 500 characters");
        }
    }

}
