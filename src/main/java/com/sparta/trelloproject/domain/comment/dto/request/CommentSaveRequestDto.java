package com.sparta.trelloproject.domain.comment.dto.request;

import com.sparta.trelloproject.domain.user.dto.UserDto;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private final Long id;
    private final String contents;
    private final UserDto user;

    public CommentSaveRequestDto(Long id, String contents, UserDto user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }
}
