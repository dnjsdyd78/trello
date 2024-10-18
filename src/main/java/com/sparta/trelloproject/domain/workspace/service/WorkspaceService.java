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
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.trelloproject.domain.user.enums.UserRole.ADMIN;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepository userRepository, WorkspaceMemberRepository workspaceMemberRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
    }

    // 워크스페이스 생성
    public WorkspaceResponse createWorkspace(WorkspaceCreateRequest request) {
        // SecurityContext에서 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        // 사용자 권한 확인 (ROLE_ADMIN인지 확인)
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AccessDeniedException("관리자만 워크스페이스를 생성할 수 있습니다.");
        }
        String adminEmail = authentication.getName();
        Workspace workspace = new Workspace(request.getName(), request.getDescription());
        User adminUser = userRepository.findByEmail(adminEmail).orElseThrow(() -> new UserNotFoundException(adminEmail));
        workspace.addMember(adminUser, ADMIN);

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


        // 현재 사용자의 이메일로 권한 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        // 사용자가 ADMIN인지 확인
        WorkspaceMember workspaceMember = workspaceMemberRepository.findFirstByWorkspaceAndUser(workspace, user)
                .orElseThrow(() -> new AccessDeniedException("워크스페이스에 접근할 권한이 없습니다."));

        if (workspaceMember.getRole() != ADMIN)
            throw new AccessDeniedException("ADMIN 권한이 있어야 수정할 수 있습니다.");

        workspace.update(request.getName(), request.getDescription());
        workspaceRepository.save(workspace);
        return new WorkspaceResponse(workspace);
    }

    public void deleteWorkspace(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        // 수정된 findFirstByWorkspaceAndUser 메서드를 사용하여 첫 번째 WorkspaceMember를 가져옴
        WorkspaceMember workspaceMember = workspaceMemberRepository.findFirstByWorkspaceAndUser(workspace, user)
                .orElseThrow(() -> new AccessDeniedException("워크스페이스에 접근할 권한이 없습니다."));

        if (workspaceMember.getRole() != ADMIN) {
            throw new AccessDeniedException("ADMIN 권한이 있어야 삭제할 수 있습니다.");
        }

        workspaceRepository.delete(workspace);
    }

}