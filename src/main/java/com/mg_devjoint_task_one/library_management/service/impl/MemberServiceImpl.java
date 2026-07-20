package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.exception.InvalidOperationException;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.MemberMapper;
import com.mg_devjoint_task_one.library_management.model.Member;
import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;
import com.mg_devjoint_task_one.library_management.repository.MemberRepository;
import com.mg_devjoint_task_one.library_management.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponse createMember(CreateMemberRequest request) {

        Member member = Member.create(request.firstName(), request.lastName(), request.email(), request.phone());

        Member savedMember = memberRepository.save(member);

        return MemberMapper.toMemberResponse(savedMember);

    }

    @Override
    public MemberResponse updateMember(UUID memberId, UpdateMemberRequest request) {

        Member memberById = getMemberEntityById(memberId);

        memberById.setFirstName(request.firstName());
        memberById.setLastName(request.lastName());
        memberById.setEmail(request.email());
        memberById.setPhone(request.phone());

        Member savedMember = memberRepository.save(memberById);

        return MemberMapper.toMemberResponse(savedMember);
    }

    @Override
    public MemberResponse getMemberById(UUID memberId) {
        Member memberById = getMemberEntityById(memberId);

        return MemberMapper.toMemberResponse(memberById);
    }

    @Override
    public void deleteMemberById(UUID memberId) {
        Member memberById = getMemberEntityById(memberId);

        if (memberById.getStatus() != MemberStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive members can be deleted. Current status: " + memberById.getStatus());
        }

        memberById.setStatus(MemberStatus.DELETED);

        memberRepository.save(memberById);
    }

    @Override
    public void activateMember(UUID memberId) {

        Member memberById = getMemberEntityById(memberId);

        if (memberById.getStatus() != MemberStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive members can be active. Current status: " + memberById.getStatus());
        }

        memberById.setStatus(MemberStatus.ACTIVE);

        memberRepository.save(memberById);

    }

    @Override
    public void deactivateMember(UUID memberId) {
        Member memberById = getMemberEntityById(memberId);

        if (memberById.getStatus() != MemberStatus.ACTIVE) {
            throw new InvalidOperationException("Only active members can be inactive. Current status: " + memberById.getStatus());
        }

        memberById.setStatus(MemberStatus.INACTIVE);

        memberRepository.save(memberById);
    }

    @Override
    public Member getMemberEntityById(UUID memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found with id " + memberId));
    }


}
