package com.sparta.trelloproject.domain.user.dto;

import com.sparta.trelloproject.domain.user.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String email;
    private String password;
    private UserRole role;
}
