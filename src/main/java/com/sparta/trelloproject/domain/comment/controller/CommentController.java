package com.sparta.trelloproject.domain.comment.controller;

import com.sparta.trelloproject.domain.comment.dto.request.CommentSaveRequestDto;
import com.sparta.trelloproject.domain.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.trelloproject.domain.comment.dto.response.CommentSaveResponseDto;
import com.sparta.trelloproject.domain.comment.dto.response.CommentUpdateResponseDto;
import com.sparta.trelloproject.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentSaveResponseDto> saveComment(@PathVariable Long commentId, @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return ResponseEntity.ok(commentService.saveComment(commentId, commentSaveRequestDto));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto){
        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }

}
