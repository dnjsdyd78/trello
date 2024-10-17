package com.sparta.trelloproject.domain.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {

    private final Long id;
    private final String contents;

    public CommentUpdateResponseDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}
