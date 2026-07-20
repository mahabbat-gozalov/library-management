package com.mg_devjoint_task_one.library_management.mapper;

import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.model.Member;

public final class MemberMapper {
    private MemberMapper() {
    }

    public static MemberResponse toMemberResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getStatus(),
                member.getMembershipDate()
        );
    }
}
