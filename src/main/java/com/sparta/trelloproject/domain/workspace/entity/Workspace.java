package com.sparta.trelloproject.domain.workspace.entity;

import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.sparta.trelloproject.domain.board.entity.Board;
import lombok.Getter;

// 워크스페이스 엔티티
@Getter
@Entity
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 워크스페이스 이름

    private String description; // 워크스페이스 설명

    // Workspace : WorkspaceMember = 1:N 관계
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkspaceMember> members = new ArrayList<>();

    // Workspace : Board = 1:N 관계
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    // 워크스페이스 생성자
    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workspace() {}

    // 멤버 추가 메서드 (User와 Role을 함께 받음)
    public void addMember(User user, UserRole role) {
        WorkspaceMember workspaceMember = new WorkspaceMember(this, user, role);  // WorkspaceMember 생성
        members.add(workspaceMember);  // 멤버 목록에 추가
        workspaceMember.setWorkspace(this);  // workspace 설정
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addMember(WorkspaceMember workspaceMember) {

    }
}