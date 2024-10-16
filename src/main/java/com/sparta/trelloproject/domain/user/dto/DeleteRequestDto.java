package com.sparta.trelloproject.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteRequestDto {
    private String email;
    private String password;
}
