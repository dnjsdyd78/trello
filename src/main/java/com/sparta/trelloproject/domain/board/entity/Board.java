package com.sparta.trelloproject.domain.board.entity;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title; // 보드 제목

    private String backgroundColor; // 보드 배경색 또는 이미지

    // Board : Workspace = N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    // Board 생성자
    public Board(String title, String backgroundColor) {
        this.title = title;
        this.backgroundColor = backgroundColor;
    }

    // 보드가 속한 워크스페이스 설정
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void update(String title, Object description) {

    }
}
