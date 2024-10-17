package com.sparta.trelloproject.domain.workspacemember.entity;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Role role; // 워크스페이스 멤버 역할 (관리자, 일반 멤버, 읽기 전용)

    // WorkspaceMember 생성자
    public WorkspaceMember(Workspace workspace, User user, Role role) {
        this.workspace = workspace;
        this.user = user;
        this.role = role;
    }

    // 멤버 역할 enum
    public enum Role {
        ADMIN, // 워크스페이스 관리자 (생성 외 모든 기능 가능)
        BOARD_MEMBER, // 보드 멤버 (워크스페이스 관련 기능 제외)
        READ_ONLY // 읽기 전용 (생성, 수정, 삭제 불가능, 조회만 가능)
    }

    // 워크스페이스 설정
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    // 멤버 역할 업데이트 메서드
    public void updateRole(Role newRole) {
        this.role = newRole;
    }
}