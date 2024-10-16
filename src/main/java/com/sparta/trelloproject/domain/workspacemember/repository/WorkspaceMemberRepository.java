package com.sparta.trelloproject.domain.workspacemember.repository;

import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
}