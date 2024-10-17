package com.sparta.trelloproject.domain.board.dto.response;

import com.sparta.trelloproject.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String backgroundColor;

    // 생성자
    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.backgroundColor = board.getBackgroundColor();
    }
}
