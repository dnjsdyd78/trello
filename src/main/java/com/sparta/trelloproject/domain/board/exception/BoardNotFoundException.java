package com.sparta.trelloproject.domain.board.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long boardId) {
        super("보드의 Id를 찾을수 없습니다: " + boardId);
    }
}