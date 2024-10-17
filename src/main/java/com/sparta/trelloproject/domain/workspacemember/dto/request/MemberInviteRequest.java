package com.sparta.trelloproject.domain.workspacemember.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class MemberInviteRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String role;

    public MemberInviteRequest(String email, String role) {
        this.email = email;
        this.role = role;
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}