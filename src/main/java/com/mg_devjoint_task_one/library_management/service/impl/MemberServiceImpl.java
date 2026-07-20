package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.exception.InvalidOperationException;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.MemberMapper;
import com.mg_devjoint_task_one.library_management.model.Member;
import com.mg_devjoint_task_one.library_management.model.enums.MemberStatus;
import com.mg_devjoint_task_one.library_management.repository.LoanRepository;
import com.mg_devjoint_task_one.library_management.repository.MemberRepository;
import com.mg_devjoint_task_one.library_management.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public MemberServiceImpl(MemberRepository memberRepository, LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public MemberResponse createMember(CreateMemberRequest request) {

        Member member = Member.create(request.firstName(), request.lastName(), request.email(), request.phone());

        Member savedMember = memberRepository.save(member);

        return MemberMapper.toMemberResponse(savedMember);

    }

    @Override
    public MemberResponse updateMember(UUID memberId, UpdateMemberRequest request) {

        Member member = getMemberEntityById(memberId);

        member.setFirstName(request.firstName());
        member.setLastName(request.lastName());
        member.setEmail(request.email());
        member.setPhone(request.phone());

        Member savedMember = memberRepository.save(member);

        return MemberMapper.toMemberResponse(savedMember);
    }

    @Override
    public MemberResponse getMemberById(UUID memberId) {
        Member member = getMemberEntityById(memberId);

        return MemberMapper.toMemberResponse(member);
    }

    @Override
    public void activateMember(UUID memberId) {

        Member member = getMemberEntityById(memberId);

        if (member.getStatus() != MemberStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive members can be activated. Current status: " + member.getStatus());
        }

        member.setStatus(MemberStatus.ACTIVE);

        memberRepository.save(member);

    }

    @Override
    public void deactivateMember(UUID memberId) {
        Member member = getMemberEntityById(memberId);

        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new InvalidOperationException("Only active members can be deactivated. Current status: " + member.getStatus());
        }

        member.setStatus(MemberStatus.INACTIVE);

        memberRepository.save(member);
    }

    @Override
    public void deleteMemberById(UUID memberId) {
        Member member = getMemberEntityById(memberId);

        if (member.getStatus() != MemberStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive members can be deleted. Current status: " + member.getStatus());
        }

        if (loanRepository.existsUnreturnedLoanByMemberId(member.getId())) {
            throw new InvalidOperationException(
                    "The member has unreturned books and cannot be deleted."
            );
        }

        member.setStatus(MemberStatus.DELETED);

        memberRepository.save(member);
    }


    @Override
    public Member getMemberEntityById(UUID memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found with id " + memberId));
    }


}
