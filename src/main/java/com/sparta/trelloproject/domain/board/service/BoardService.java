package com.sparta.trelloproject.domain.board.service;

import com.sparta.trelloproject.domain.board.dto.request.BoardCreateRequest;
import com.sparta.trelloproject.domain.board.dto.request.BoardUpdateRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.exception.BoardNotFoundException;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.exception.WorkspaceNotFoundException;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    public BoardService(BoardRepository boardRepository, WorkspaceRepository workspaceRepository) {
        this.boardRepository = boardRepository;
        this.workspaceRepository = workspaceRepository;
    }

    // 보드 생성
    public BoardResponse createBoard(Long workspaceId, BoardCreateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        Board board = new Board(request.getTitle(), request.getBackgroundColor());
        board.setWorkspace(workspace);
        boardRepository.save(board);
        return new BoardResponse(board);
    }

    // 보드 목록 조회
    public List<BoardResponse> getAllBoards(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        List<Board> boards = boardRepository.findAllByWorkspace(workspace);
        return boards.stream().map(BoardResponse::new).collect(Collectors.toList());
    }

    // 보드 상세 조회
    public BoardResponse getBoard(Long workspaceId, Long boardId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        Board board = boardRepository.findByIdAndWorkspace(boardId, workspace)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
        return new BoardResponse(board);
    }

    // 보드 수정
    public BoardResponse updateBoard(Long workspaceId, Long boardId, BoardUpdateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        Board board = boardRepository.findByIdAndWorkspace(boardId, workspace)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
        board.update(request.getTitle(), request.getBackgroundColor());
        boardRepository.save(board);
        return new BoardResponse(board);
    }

    // 보드 삭제
    public void deleteBoard(Long workspaceId, Long boardId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        Board board = boardRepository.findByIdAndWorkspace(boardId, workspace)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
        boardRepository.delete(board);
    }
}