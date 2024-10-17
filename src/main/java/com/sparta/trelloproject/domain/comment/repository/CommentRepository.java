package com.sparta.trelloproject.domain.comment.repository;

import com.sparta.trelloproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCardIdAndId(Long cardId, Long commentId);
}
