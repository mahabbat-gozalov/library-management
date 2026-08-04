package com.mg_devjoint.library_management.dto.criteria;

import com.mg_devjoint.library_management.model.enums.MemberStatus;

import java.time.LocalDate;

public record MemberSearchCriteria(
        String firstName,
        String lastName,
        String email,
        String phone,
        MemberStatus status,
        LocalDate membershipDateSince,
        LocalDate membershipDateUntil
) {
}
