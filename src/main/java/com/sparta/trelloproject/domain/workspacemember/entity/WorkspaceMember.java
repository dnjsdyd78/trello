package com.sparta.trelloproject.domain.workspacemember.entity;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
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

    @Enumerated(EnumType.STRING)
    private ZRole zrole; // 워크스페이스 멤버 역할 (관리자, 일반 멤버, 읽기 전용)

    public WorkspaceMember() {
    }

    // 멤버 역할 enum
    public enum ZRole {
        ADMIN, // 워크스페이스 관리자 (생성 외 모든 기능 가능)
        READ_ONLY; // 읽기 전용 (생성, 수정, 삭제 불가능, 조회만 가능);
    }

    // WorkspaceMember 생성자
    public WorkspaceMember(Workspace workspace, User user, UserRole role) {
        this.workspace = workspace;
        this.user = user;
        this.role = role;
    }

    // 카드, 리스트, 매니저 역할
    public WorkspaceMember(Workspace workspace, User user, ZRole zrole) {
        this.workspace = workspace;
        this.user = user;
        this.zrole = zrole;
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