package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.Card;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardQueryDslRepository {
    Optional<Card> findByIdWithDetails(@Param("cardId") Long cardId);
}
