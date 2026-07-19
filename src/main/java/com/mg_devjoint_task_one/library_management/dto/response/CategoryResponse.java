package com.mg_devjoint_task_one.library_management.dto.response;

import java.util.Set;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description,
        Set<UUID> categoryBookIdSet) {
}
