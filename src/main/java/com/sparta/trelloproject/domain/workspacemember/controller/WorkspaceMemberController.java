package com.sparta.trelloproject.domain.workspacemember.controller;

import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberInviteRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberRoleUpdateRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.response.MemberResponse;
import com.sparta.trelloproject.domain.workspacemember.service.WorkspaceMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/members")
public class WorkspaceMemberController {

    private final WorkspaceMemberService workspaceMemberService;

    public WorkspaceMemberController(WorkspaceMemberService workspaceMemberService) {
        this.workspaceMemberService = workspaceMemberService;
    }

    // 멤버 초대
    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(@PathVariable Long workspaceId,
                                                       @RequestBody @Valid MemberInviteRequest request) {
        MemberResponse response = workspaceMemberService.inviteMember(workspaceId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 멤버 목록 조회
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable Long workspaceId) {
        List<MemberResponse> members = workspaceMemberService.getMembers(workspaceId);
        return ResponseEntity.ok(members);
    }

    // 멤버 역할 수정
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(@PathVariable Long workspaceId,
                                                           @PathVariable Long memberId,
                                                           @RequestBody @Valid MemberRoleUpdateRequest request) {
        MemberResponse response = workspaceMemberService.updateMemberRole(workspaceId, memberId, request);
        return ResponseEntity.ok(response);
    }

    // 멤버 제거
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long workspaceId, @PathVariable Long memberId) {
        workspaceMemberService.removeMember(workspaceId, memberId);
        return ResponseEntity.noContent().build();
    }
}