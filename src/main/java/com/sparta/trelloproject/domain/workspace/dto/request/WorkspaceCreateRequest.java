package com.sparta.trelloproject.domain.workspace.dto.request;

import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceCreateRequest {
    private String name;
    private String description;
    private List<String> members; // 이메일 목록으로 초대할 멤버들
    private UserRole defaultRole; // 멤버에게 부여할 기본 역할

    public WorkspaceCreateRequest(String name, String description, List<String> members, UserRole defaultRole) {
        this.name = name;
        this.description = description;
        this.members = members;
        this.defaultRole = defaultRole;
    }
}