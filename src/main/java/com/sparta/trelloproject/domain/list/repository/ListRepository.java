package com.sparta.trelloproject.domain.list.repository;

import com.sparta.trelloproject.domain.list.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
    List<ListEntity> findByBoardId(Long id);

    boolean existsByBoardIdAndSequence(Long boardId, Integer sequence);
}
