package com.sparta.trelloproject.domain.workspace.repository;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByName(String name); // 워크스페이스 이름으로 검색
}
