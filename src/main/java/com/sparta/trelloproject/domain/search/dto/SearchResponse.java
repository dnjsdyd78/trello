package com.sparta.trelloproject.domain.search.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchResponse {
    private final Long boardId;
    private final Long cardId;
    private final String title;
    private final String contents;
    private final LocalDateTime deadline;


    @QueryProjection
    public SearchResponse(Long boardId, Long cardId, String title, String contents, LocalDateTime deadline) {
        this.boardId = boardId;
        this.cardId = cardId;
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;

    }
}

