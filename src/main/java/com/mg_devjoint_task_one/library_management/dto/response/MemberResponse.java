package com.mg_devjoint_task_one.library_management.dto.response;

import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;

import java.time.LocalDate;
import java.util.UUID;

public record MemberResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        MemberStatus status,
        LocalDate membershipDate
) {
}
