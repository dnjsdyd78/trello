package com.sparta.trelloproject.domain.workSpaceMameber.repository;

import com.sparta.trelloproject.domain.workSpaceMameber.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
}
