package com.sparta.trelloproject.domain.workspace.entity;

import com.sparta.trelloproject.domain.user.entity.User;
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

    // 워크스페이스에 멤버 추가
    public void addMember(User user, WorkspaceMember.Role role) {
        WorkspaceMember member = new WorkspaceMember(user, role); // 새로운 멤버 생성
        member.setWorkspace(this);  // 새 멤버의 워크스페이스 설정
        members.add(member);        // 멤버 리스트에 추가
    }

    // 워크스페이스에 보드 추가
    public void addBoard(Board board) {
        boards.add(board);
        board.setWorkspace(this);
    }

    public void update(String name, String description) {
    }
}