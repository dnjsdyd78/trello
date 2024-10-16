package com.sparta.trelloproject.domain.workspacemember.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInviteRequest {

    @NotNull(message = "이메일은 필수 입력입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotNull(message = "역할은 필수 입력입니다.")
    private Role role;
}