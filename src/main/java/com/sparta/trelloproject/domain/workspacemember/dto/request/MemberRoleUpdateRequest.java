package com.sparta.trelloproject.domain.workspacemember.dto.request;

import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import jakarta.validation.constraints.NotNull;

public class MemberRoleUpdateRequest {

    @NotNull(message = "역할은 필수입니다.")
    private WorkspaceMember.Role role;

    // Getter
    public WorkspaceMember.Role getRole() {
        return role;
    }

    // Setter (필요할 경우 추가)
    public void setRole(WorkspaceMember.Role role) {
        this.role = role;
    }
}