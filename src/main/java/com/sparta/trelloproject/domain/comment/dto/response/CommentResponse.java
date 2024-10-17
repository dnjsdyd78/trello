package com.sparta.trelloproject.domain.comment.dto.response;

import com.sparta.trelloproject.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private final Long id;
    private final String comment;

    // Comment 매개변수를 받는 생성자
    public CommentResponse(Comment comment) {
        this.id = comment.getCommentId(); // 적절한 메서드로 ID 가져오기
        this.comment = comment.getComment(); // 적절한 메서드로 내용 가져오기
        // 다른 필드도 초기화
    }
}
