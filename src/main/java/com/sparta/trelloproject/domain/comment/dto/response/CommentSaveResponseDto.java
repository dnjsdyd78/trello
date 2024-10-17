package com.sparta.trelloproject.domain.comment.dto.response;

import com.sparta.trelloproject.domain.user.dto.UserDto;
import lombok.Getter;

@Getter
public class CommentSaveResponseDto {

    private final Long id;
    private final String contents;
    private final UserDto user;

    public CommentSaveResponseDto(Long id, String contents, UserDto user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }
}
