package com.sparta.trelloproject.domain.search.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private final Long boardId;
    private final Card card;

    @QueryProjection
    public SearchResponse(Long boardId, Card card) {
        this.boardId = boardId;
        this.card = card;
    }
}
