package com.mg_devjoint_task_one.library_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record UpdateCategoryRequest(
        @NotBlank(message = "name cannot be blank")
        @Size(max = 100, message = "name must be at most 100 characters")
        String name,

        @Size(max = 500, message = "description must be at most 500 characters")
        String description,

        Set<UUID> bookIdSet,

        BookUpdateMode bookUpdateMode
) {
    public enum BookUpdateMode {
        ADD,
        REPLACE
    }
}