package com.sparta.trelloproject.domain.list.repository;

import com.sparta.trelloproject.domain.list.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
}
