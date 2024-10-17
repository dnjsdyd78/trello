package com.sparta.trelloproject.domain.workspacemember.dto.response;

import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
public class MemberResponse {
    private Long memberId;
    private String email;
    private String role;

    public MemberResponse(Long memberId, String email, String role) {
        this.memberId = memberId;
        this.email = email;
        this.role = role;
    }

    public MemberResponse(WorkspaceMember workspaceMember) {
    }

    // Getter methods
    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}