package com.sparta.trelloproject.domain.board.entity;

import com.sparta.trelloproject.common.entity.Timestamped;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Board extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;
}
