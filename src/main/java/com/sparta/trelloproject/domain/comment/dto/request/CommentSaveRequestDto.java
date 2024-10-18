package com.sparta.trelloproject.domain.comment.dto.request;

import com.sparta.trelloproject.domain.user.dto.UserDto;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private final String contents;
    private final UserDto user;

    public CommentSaveRequestDto(String contents, UserDto user) {
        this.contents = contents;
        this.user = user;
    }
}
