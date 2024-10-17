package com.sparta.trelloproject.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private String contents;
    private Long userId;
}
