package com.mg_devjoint.library_management.service;

import com.mg_devjoint.library_management.dto.criteria.MemberSearchCriteria;
import com.mg_devjoint.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint.library_management.dto.response.MemberResponse;
import com.mg_devjoint.library_management.dto.response.PageResponse;
import com.mg_devjoint.library_management.model.Member;

import java.util.UUID;

public interface MemberService {

    MemberResponse createMember(CreateMemberRequest request);

    PageResponse<MemberResponse> getAllMembers(int page, int size);

    MemberResponse getMemberById(UUID memberId);

    MemberResponse updateMember(UUID memberId, UpdateMemberRequest request);

    void deleteMemberById(UUID memberId);

    void activateMember(UUID memberId);

    void deactivateMember(UUID memberId);

    Member getMemberEntityById(UUID memberId);

    PageResponse<MemberResponse> filter(MemberSearchCriteria criteria, int page, int size);
}
