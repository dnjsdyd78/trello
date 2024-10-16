package com.sparta.trelloproject.domain.comment.service;

import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.comment.dto.request.CommentSaveRequestDto;
import com.sparta.trelloproject.domain.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.trelloproject.domain.comment.dto.response.CommentSaveResponseDto;
import com.sparta.trelloproject.domain.comment.dto.response.CommentUpdateResponseDto;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.comment.repository.CommentRepository;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.user.dto.UserDto;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CommentSaveResponseDto saveComment(Long commentId, CommentSaveRequestDto commentSaveRequestDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NullPointerException("카드를 찾을수 없습니다."));

        User user = userRepository.findById(commentSaveRequestDto.getId()).orElseThrow(() ->
                new NullPointerException("사용자를 찾을수 없습니다."));

        Comment comment = new Comment(commentSaveRequestDto.getContents(), card, user);
        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                new UserDto(user.getId(), user.getEmail()));
    }

    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("댓글을 찾을수 없습니다."));

        comment.update(commentUpdateRequestDto.getContents());
        return new CommentUpdateResponseDto(comment.getId(), comment.getContents());
    }

    public void deleteComment(Long commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new NullPointerException("댓글을 찾을수 없습니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
