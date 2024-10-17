package com.sparta.trelloproject.domain.workspacemember.repository;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    List<WorkspaceMember> findAllByWorkspace(Workspace workspace);
    Optional<WorkspaceMember> findByIdAndWorkspace(Long id, Workspace workspace);

}