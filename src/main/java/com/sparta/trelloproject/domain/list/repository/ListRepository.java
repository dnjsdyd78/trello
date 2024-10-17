package com.sparta.trelloproject.domain.list.repository;

import com.sparta.trelloproject.domain.list.entity.ListEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
    // 비관적 락을 적용한 리스트 조회 메서드
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM ListEntity l WHERE l.listId = :id")
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<ListEntity> findByIdWithPessimisticLock(Long id);
}
