package com.sparta.trelloproject.domain.workspacemember.repository;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    List<WorkspaceMember> findAllByWorkspace(Workspace workspace);
    Optional<WorkspaceMember> findByIdAndWorkspace(Long id, Workspace workspace);
    // 워크스페이스와 사용자로 워크스페이스 멤버 조회
    Optional<WorkspaceMember> findByWorkspaceAndUser(Workspace workspace, User user);

    Optional<WorkspaceMember> findFirstByWorkspaceAndUser(Workspace workspace, User user);

    // userId와 workspaceId를 기반으로 WorkspaceMember 조회
    Optional<WorkspaceMember> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);


}