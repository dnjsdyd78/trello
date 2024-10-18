package com.sparta.trelloproject.domain.list.entity;

import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class ListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "listEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public ListEntity(String title, Integer sequence, Board board) {
        this.title = title;
        this.sequence = sequence;
        this.board = board;
    }

    // 타이틀 업데이트 메서드
    public void updateTitle(String title) {
        this.title = title;
    }

    // 시퀀스 업데이트 메서드
    public void updateSequence(Integer sequence) {
        this.sequence = sequence;
    }

}


