package com.sparta.trelloproject.domain.workspacemember.service;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.exception.UserNotFoundException;
import com.sparta.trelloproject.domain.workspace.exception.WorkspaceNotFoundException;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberInviteRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberRoleUpdateRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.response.MemberResponse;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.exception.MemberNotFoundException;
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

        // 이메일로 User 조회 (User가 필요함)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        // String을 WorkspaceMember.Role로 변환
        WorkspaceMember.Role role = WorkspaceMember.Role.valueOf(request.getRole().toUpperCase());

        // WorkspaceMember 객체 생성
        WorkspaceMember workspaceMember = new WorkspaceMember(workspace, user, role);

        // Workspace에 멤버 추가
        workspace.addMember(workspaceMember);

        // 변경된 Workspace 저장
        workspaceRepository.save(workspace);

        // 새로운 멤버 추가 후 MemberResponse 반환
        return new MemberResponse(workspaceMember);
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