package com.mg_devjoint.library_management.dto.criteria;

import com.mg_devjoint.library_management.model.enums.BookStatus;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;
import java.util.UUID;

public record BookSearchCriteria(
        String title,

        BookStatus status,

        Set<UUID> categoryIdSet,

        Set<UUID> authorIdSet,

        @PositiveOrZero(message = "Minimum full quantity must be greater than or equal to 0")
        Integer minFullQuantity,

        @PositiveOrZero(message = "Maximum full quantity must be greater than or equal to 0")
        Integer maxFullQuantity,

        @PositiveOrZero(message = "Minimum available quantity must be greater than or equal to 0")
        Integer minAvailableQuantity,

        @PositiveOrZero(message = "Maximum available quantity must be greater than or equal to 0")
        Integer maxAvailableQuantity
) {

}
