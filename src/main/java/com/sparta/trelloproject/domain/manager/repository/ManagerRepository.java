package com.sparta.trelloproject.domain.manager.repository;

import com.sparta.trelloproject.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
