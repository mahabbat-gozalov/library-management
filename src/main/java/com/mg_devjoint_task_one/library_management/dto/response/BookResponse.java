package com.mg_devjoint_task_one.library_management.dto.response;

import java.util.Set;
import java.util.UUID;

public record BookResponse (
        UUID id,
        String title,
        String isbn,
        String description,
        Integer fullQuantity,
        Integer availableQuantity,
        Set<UUID> authorIdSet,
        Set<UUID> categoryIdSet
){
}
