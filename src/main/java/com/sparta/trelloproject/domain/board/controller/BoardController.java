package com.sparta.trelloproject.domain.board.controller;

import com.sparta.trelloproject.domain.board.dto.request.BoardCreateRequest;
import com.sparta.trelloproject.domain.board.dto.request.BoardUpdateRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.board.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@PathVariable Long workspaceId,
                                                     @RequestBody @Valid BoardCreateRequest request) {
        BoardResponse response = boardService.createBoard(workspaceId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 보드 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards(@PathVariable Long workspaceId) {
        List<BoardResponse> boards = boardService.getAllBoards(workspaceId);
        return ResponseEntity.ok(boards);
    }

    // 보드 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long workspaceId,
                                                  @PathVariable Long boardId) {
        BoardResponse response = boardService.getBoard(workspaceId, boardId);
        return ResponseEntity.ok(response);
    }

    // 보드 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long workspaceId,
                                                     @PathVariable Long boardId,
                                                     @RequestBody @Valid BoardUpdateRequest request) {
        BoardResponse response = boardService.updateBoard(workspaceId, boardId, request);
        return ResponseEntity.ok(response);
    }

    // 보드 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long workspaceId,
                                            @PathVariable Long boardId) {
        boardService.deleteBoard(workspaceId, boardId);
        return ResponseEntity.noContent().build();
    }
}