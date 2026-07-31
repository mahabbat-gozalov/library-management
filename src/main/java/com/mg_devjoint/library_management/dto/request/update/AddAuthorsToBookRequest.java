package com.mg_devjoint.library_management.dto.request.update;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record AddAuthorsToBookRequest(
        @NotEmpty(message = "Author Set cannot be null and it must contain at least one element")
        Set<UUID> authorIdSet
) {
}