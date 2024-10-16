package com.sparta.trelloproject.domain.workspace.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.sparta.trelloproject.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.sparta.trelloproject.domain.workspace.dto.response.WorkspaceResponse;
import com.sparta.trelloproject.domain.workspace.service.WorkspaceService;
import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberInviteRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.response.MemberResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    // 워크스페이스 생성
    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(@RequestBody @Valid WorkspaceCreateRequest request) {
        WorkspaceResponse response = workspaceService.createWorkspace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 워크스페이스 목록 조회
    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> getAllWorkspaces() {
        List<WorkspaceResponse> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    // 워크스페이스 수정
    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> updateWorkspace(@PathVariable Long workspaceId,
                                                             @RequestBody @Valid WorkspaceUpdateRequest request) {
        WorkspaceResponse response = workspaceService.updateWorkspace(workspaceId, request);
        return ResponseEntity.ok(response);
    }

    // 워크스페이스 삭제
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return ResponseEntity.noContent().build();
    }

    // 멤버 초대
    @PostMapping("/{workspaceId}/members")
    public ResponseEntity<MemberResponse> inviteMember(@PathVariable Long workspaceId,
                                                       @RequestBody @Valid MemberInviteRequest request) {
        MemberResponse response = workspaceService.inviteMember(workspaceId, request);
        return ResponseEntity.ok(response);
    }
}