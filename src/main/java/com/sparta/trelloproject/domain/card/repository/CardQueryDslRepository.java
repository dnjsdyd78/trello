package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CardQueryDslRepository {
    Optional<Card> findByIdWithDetails(@Param("cardId") Long cardId);
    Page<SearchResponse> searchCardByBoardAndTitleWithContentsAndAssigneeWithinDeadlineRange(
            Pageable pageable, Long boardId, String title, String contents,
            String assignee, LocalDateTime fromDate, LocalDateTime toDate
    );
}
