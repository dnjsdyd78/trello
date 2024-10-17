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
    private final List<Comment> comments;
    private final List<String> assigneeEmails;

    @QueryProjection
    public SearchResponse(Long boardId, Long cardId, String title, String contents, LocalDateTime deadline, List<Comment> comments, List<String> assigneeEmails) {
        this.boardId = boardId;
        this.cardId = cardId;
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.comments = comments;
        this.assigneeEmails = assigneeEmails;
    }
}

