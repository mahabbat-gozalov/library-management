package com.mg_devjoint.library_management.dto.criteria;

import java.util.Set;
import java.util.UUID;

public record AuthorSearchCriteria(
        String firstName,

        String lastName,

        String summary,

        String email,

        Set<UUID> bookIdSet

) {
}
