package com.sparta.trelloproject.domain.workspacemember.dto.request;

import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import jakarta.validation.constraints.NotNull;

public class MemberRoleUpdateRequest {

    @NotNull(message = "역할은 필수입니다.")
    private UserRole role;

    // Getter
    public UserRole getRole() {
        return role;
    }

    // Setter (필요할 경우 추가)
    public void setRole(UserRole role) {
        this.role = role;
    }
}