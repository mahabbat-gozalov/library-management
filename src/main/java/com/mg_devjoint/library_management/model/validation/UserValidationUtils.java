package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.enums.UserRole;

public final class UserValidationUtils {

    private UserValidationUtils() {
    }

    public static void validateUserRole(final UserRole userRole) {
        if (userRole == null) {
            throw new InvalidEntityDataException("UserRole cannot be null");
        }
    }

    public static void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new InvalidEntityDataException("Password cannot be null or blank");
        }
    }

    public static void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidEntityDataException("Name cannot be null or blank");
        }
    }

    public static void validateSurname(final String surname) {
        if (surname == null || surname.isBlank()) {
            throw new InvalidEntityDataException("Surname cannot be null or blank");
        }
    }

}
