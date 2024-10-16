package com.sparta.trelloproject.domain.workspacemember.service;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceMemberService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public WorkspaceMemberService(WorkspaceRepository workspaceRepository,
                                  UserRepository userRepository,
                                  WorkspaceMemberRepository workspaceMemberRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
    }

    // 멤버 초대
    public MemberResponse inviteMember(Long workspaceId, MemberInviteRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));
        WorkspaceMember member = new WorkspaceMember(workspace, user, request.getRole());
        workspaceMemberRepository.save(member);
        return new MemberResponse(member);
    }

    // 멤버 목록 조회
    public List<MemberResponse> getMembers(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        List<WorkspaceMember> members = workspaceMemberRepository.findAllByWorkspace(workspace);
        return members.stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    // 멤버 역할 수정
    public MemberResponse updateMemberRole(Long workspaceId, Long memberId, MemberRoleUpdateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        WorkspaceMember member = workspaceMemberRepository.findByIdAndWorkspace(memberId, workspace)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        member.updateRole(request.getRole());
        workspaceMemberRepository.save(member);
        return new MemberResponse(member);
    }

    // 멤버 제거
    public void removeMember(Long workspaceId, Long memberId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        WorkspaceMember member = workspaceMemberRepository.findByIdAndWorkspace(memberId, workspace)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        workspaceMemberRepository.delete(member);
    }
}