package com.mg_devjoint_task_one.library_management.dto.request;

import java.util.Set;
import java.util.UUID;

public record CreateCategoryRequest(
        String name,
        String description,
        Set<UUID> initialBookIdSet
) {
}
