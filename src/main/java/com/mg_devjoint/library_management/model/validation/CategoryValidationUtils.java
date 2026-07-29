package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;

public final class CategoryValidationUtils {
    private CategoryValidationUtils() {
    }

    public static void validateCategoryName(final String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new InvalidEntityDataException("Category name cannot be null or blank");
        }
    }

    public static void validateDescription(final String description) {
        if (description == null) return;

        if (description.length() > 500) {
            throw new InvalidEntityDataException("Category description cannot be longer than 500 characters");
        }
    }

}
