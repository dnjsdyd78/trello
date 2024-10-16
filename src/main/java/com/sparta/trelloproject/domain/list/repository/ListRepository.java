package com.sparta.trelloproject.domain.list.repository;

import com.sparta.trelloproject.domain.list.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
