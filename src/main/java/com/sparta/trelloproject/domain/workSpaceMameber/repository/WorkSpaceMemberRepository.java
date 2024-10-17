package com.sparta.trelloproject.domain.workSpaceMameber.repository;

import com.sparta.trelloproject.domain.workSpaceMameber.entity.WorkSpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceMemberRepository extends JpaRepository<WorkSpaceMember, Long> {
}
