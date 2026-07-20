package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.model.Member;

import java.util.UUID;

public interface MemberService {

    MemberResponse createMember(CreateMemberRequest request);

    MemberResponse updateMember(UUID memberId, UpdateMemberRequest request);

    MemberResponse getMemberById(UUID memberId);

    void deleteMemberById(UUID memberId);

    Member getMemberEntityById(UUID memberId);

    void activateMember(UUID memberId);

    void deactivateMember(UUID memberId);

}
