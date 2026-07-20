package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    ResponseEntity<MemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{memberId}")
    ResponseEntity<MemberResponse> updateMember(@PathVariable UUID memberId, @Valid @RequestBody UpdateMemberRequest request) {
        MemberResponse response = memberService.updateMember(memberId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}")
    ResponseEntity<MemberResponse> getMemberById(@PathVariable UUID memberId) {
        MemberResponse response = memberService.getMemberById(memberId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberId}")
    ResponseEntity<Void> deleteMemberById(@PathVariable UUID memberId) {
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}/activate")
    public ResponseEntity<Void> activateMember(@PathVariable UUID memberId) {
        memberService.activateMember(memberId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}/deactivate")
    public ResponseEntity<Void> deactivateMember(@PathVariable UUID memberId) {
        memberService.deactivateMember(memberId);

        return ResponseEntity.noContent().build();
    }

}
