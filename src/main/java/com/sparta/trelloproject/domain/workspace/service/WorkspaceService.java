package com.sparta.trelloproject.domain.workspace.service;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.sparta.trelloproject.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.sparta.trelloproject.domain.workspace.dto.response.WorkspaceResponse;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.exception.UserNotFoundException;
import com.sparta.trelloproject.domain.workspace.exception.WorkspaceNotFoundException;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import com.sparta.trelloproject.domain.workspacemember.dto.request.MemberInviteRequest;
import com.sparta.trelloproject.domain.workspacemember.dto.response.MemberResponse;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    // 워크스페이스 생성
    public WorkspaceResponse createWorkspace(WorkspaceCreateRequest request) {
        Workspace workspace = new Workspace(request.getName(), request.getDescription());

        // 회원 초대 로직 포함
        if (request.getMembers() != null && !request.getMembers().isEmpty()) {
            for (String email : request.getMembers()) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(email));

                // 기본 역할을 함께 추가 (request에서 defaultRole 가져오기)
                workspace.addMember(user, request.getDefaultRole());
            }
        }

        workspaceRepository.save(workspace);
        return new WorkspaceResponse(workspace);
    }

    // 워크스페이스 목록 조회
    public List<WorkspaceResponse> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        return workspaces.stream().map(WorkspaceResponse::new).collect(Collectors.toList());
    }

    // 워크스페이스 수정
    public WorkspaceResponse updateWorkspace(Long workspaceId, WorkspaceUpdateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        workspace.update(request.getName(), request.getDescription());
        workspaceRepository.save(workspace);
        return new WorkspaceResponse(workspace);
    }

    // 워크스페이스 삭제
    public void deleteWorkspace(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        workspaceRepository.delete(workspace);
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
}