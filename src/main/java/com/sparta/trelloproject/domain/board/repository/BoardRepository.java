package com.sparta.trelloproject.domain.board.repository;

import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitle(String title); // 보드 제목으로 검색
    List<Board> findAllByWorkspace(Workspace workspace);

    Optional<Board> findByIdAndWorkspace(Long boardId, Workspace workspace);
}
