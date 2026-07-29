package com.mg_devjoint.library_management.model.validation;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;

import java.util.UUID;
import java.util.regex.Pattern;

public class CommonValidationUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$");

    public static void validateEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEntityDataException("Email cannot be null or blank");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEntityDataException("Invalid Email format.");
        }
    }

    public static void validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new InvalidEntityDataException("Phone number cannot be null or blank");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidEntityDataException("Invalid phone number format.");
        }
    }

    public static void validateIdCannotBeNull(final UUID id) {
        if (id == null) {
            throw new InvalidEntityDataException("Id cannot be null");
        }
    }

}
