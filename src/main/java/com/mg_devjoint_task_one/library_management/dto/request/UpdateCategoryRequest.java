package com.mg_devjoint_task_one.library_management.dto.request;

import com.mg_devjoint_task_one.library_management.dto.request.enums.CollectionUpdateMode;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

public record UpdateCategoryRequest(

        @NotBlank(message = "Category name cannot be blank")
        @Size(max = 100, message = "Category name must be at most 100 characters")
        String name,

        @Size(max = 500, message = "Category description must be at most 500 characters")
        String description,

        Set<@NotNull(message = "Book ID cannot be null") UUID> bookIdSet,

        CollectionUpdateMode bookSetUpdateMode

) {
}