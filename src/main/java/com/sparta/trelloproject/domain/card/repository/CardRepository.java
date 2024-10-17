package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardQueryDslRepository {
    // 기본 조회 메서드 (락 없음)
    Optional<Card> findById(Long id);

    // 비관적 락을 사용하는 조회 메서드
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT c FROM Card c WHERE c.id = :id")
    Optional<Card> findByIdWithReadLock(Long id);
}
