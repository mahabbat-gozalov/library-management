package com.mg_devjoint.library_management.model.validation;

import java.util.Objects;
import java.util.Set;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.Author;
import com.mg_devjoint.library_management.model.Category;
import com.mg_devjoint.library_management.model.enums.BookStatus;

public final class BookValidationUtils {
    private BookValidationUtils() {
    }

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidEntityDataException("Title cannot be null or blank");
        }
        if (title.length() > 255) {
            throw new InvalidEntityDataException("Title must be at most 255 characters");
        }

    }

    public static void validateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new InvalidEntityDataException("Isbn cannot be null or blank");
        }
        if (isbn.length() > 20) {
            throw new InvalidEntityDataException("Isbn must be at most 20 characters");
        }
    }

    public static void validateDescription(String description) {
        if(description == null) return;
        
        if (description.length() > 2000) {
            throw new InvalidEntityDataException("Description must be at most 2000 characters");
        }
    }

    public static void validateFullQuantityNullOrNegative(Integer fullQuantity) {
        if (fullQuantity == null || fullQuantity < 0) {
            throw new InvalidEntityDataException("Full quantity cannot be null or negative");
        }
    }

    public static void validateBookStatus(BookStatus status) {
        if (status == null) {
            throw new InvalidEntityDataException("Book status cannot be null");
        }
    }

    public static void validateInitialAuthorSet(Set<Author> initialAuthorSet) {
        if (initialAuthorSet == null) return;

        boolean containsNull = initialAuthorSet.stream()
                .anyMatch(Objects::isNull);

        if (containsNull) {
            throw new InvalidEntityDataException("Initial author set cannot contain null");
        }
    }

    public static void validateInitialCategorySet(Set<Category> initialCategorySet) {
        if (initialCategorySet == null) return;

        boolean containsNull = initialCategorySet.stream()
                .anyMatch(Objects::isNull);

        if (containsNull) {
            throw new InvalidEntityDataException("Initial category set cannot contain null");
        }
    }
}
