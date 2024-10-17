package com.sparta.trelloproject.domain.workspacemember.entity;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace; // N:1 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // N:1 관계

    @Enumerated(EnumType.STRING)
    private UserRole role; // 워크스페이스 멤버 역할 (관리자, 일반 멤버, 읽기 전용)

    public WorkspaceMember() {
    }

    // WorkspaceMember 생성자
    public WorkspaceMember(Workspace workspace, User user, UserRole role) {
        this.workspace = workspace;
        this.user = user;
        this.role = role;
    }

    // 워크스페이스 설정
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    // 멤버 역할 업데이트 메서드
    public void updateRole(UserRole newRole) {
        this.role = newRole;
    }
}