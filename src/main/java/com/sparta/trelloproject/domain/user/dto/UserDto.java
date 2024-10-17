package com.sparta.trelloproject.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private final Long id;
    private final String email;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}