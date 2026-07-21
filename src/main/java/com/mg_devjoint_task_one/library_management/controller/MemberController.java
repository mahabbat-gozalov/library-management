package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateMemberRequest;
import com.mg_devjoint_task_one.library_management.dto.response.MemberResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Members", description = "Member management endpoints")
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "Register a new member",
            description = "Creates a new member with ACTIVE status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all members",
            description = "Returns a paginated list of members. Page numbering starts at 1."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponse<MemberResponse>> getAllMembers(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size
    ) {
        var body = memberService.getAllMembers(page, size);

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get a member by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable UUID memberId) {
        MemberResponse response = memberService.getMemberById(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing member",
            description = "Updates a member's personal information. All fields are required (full replace semantics)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable UUID memberId,
                                                       @Valid @RequestBody UpdateMemberRequest request) {
        MemberResponse response = memberService.updateMember(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a member",
            description = "Soft-deletes a member (status set to DELETED, permanent/terminal). " +
                    "Only allowed for members that are currently INACTIVE and have no unreturned loans."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "400", description = "Member is not INACTIVE, or has unreturned loans")
    })
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable UUID memberId) {
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Activate a member",
            description = "Transitions a member from INACTIVE to ACTIVE status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member activated successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "400", description = "Member is not in INACTIVE status")
    })
    @PatchMapping("/{memberId}/activate")
    public ResponseEntity<Void> activateMember(@PathVariable UUID memberId) {
        memberService.activateMember(memberId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deactivate a member",
            description = "Transitions a member from ACTIVE to INACTIVE status " +
                    "(e.g. when the member has an overdue loan)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "400", description = "Member is not in ACTIVE status")
    })
    @PatchMapping("/{memberId}/deactivate")
    public ResponseEntity<Void> deactivateMember(@PathVariable UUID memberId) {
        memberService.deactivateMember(memberId);

        return ResponseEntity.noContent().build();
    }

}