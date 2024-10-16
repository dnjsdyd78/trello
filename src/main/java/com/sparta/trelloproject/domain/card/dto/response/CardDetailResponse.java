package com.sparta.trelloproject.domain.card.dto.response;

import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardDetailResponse {
    private final Long cardId;
    private final String title;
    private final String content;
    private final LocalDateTime deadLine;
    private final List<CommentResponse> comments; // 댓글 정보를 담는 리스트
    private final List<ManagerResponse> managers; // 매니저 정보를 담는 리스트
}
