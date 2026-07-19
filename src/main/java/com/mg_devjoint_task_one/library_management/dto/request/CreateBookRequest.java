package com.mg_devjoint_task_one.library_management.dto.request;

import com.mg_devjoint_task_one.library_management.model.enums.BookStatus;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

public record CreateBookRequest(

        @NotBlank(message = "Book title cannot be blank")
        @Size(max = 255, message = "Book title must be at most 255 characters")
        String title,

        @NotBlank(message = "ISBN cannot be blank")
        @Size(max = 20, message = "ISBN must be at most 20 characters")
        String isbn,

        @Size(max = 2000, message = "Book description must be at most 2000 characters")
        String description,

        @NotNull(message = "Full quantity cannot be null")
        @PositiveOrZero(message = "Full quantity must be at least 0")
        Integer fullQuantity,

        Set<@NotNull(message = "Author ID cannot be null") UUID> authorIdSet,

        Set<@NotNull(message = "Category ID cannot be null") UUID> categoryIdSet
) {
}