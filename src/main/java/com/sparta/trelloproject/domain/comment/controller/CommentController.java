package com.sparta.trelloproject.domain.comment.controller;

import com.sparta.trelloproject.common.annotation.SendAlert;
import com.sparta.trelloproject.common.apipayload.ApiResponse;
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

    @SendAlert
    @PostMapping("/cards/{cardId}/comments")
    public ApiResponse<CommentSaveResponseDto> saveComment(@PathVariable Long cardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return ApiResponse.onSuccess(commentService.saveComment(cardId, commentSaveRequestDto));
    }

    @PutMapping("/cards/{cardId}/comments/{commentId}")
    public ApiResponse<CommentUpdateResponseDto> updateComment(@PathVariable Long cardId, @PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto){
        return ApiResponse.onSuccess(commentService.updateComment(cardId, commentId, commentUpdateRequestDto));
    }

    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long cardId, @PathVariable Long commentId){
        commentService.deleteComment(cardId, commentId);
        return ApiResponse.onSuccess(null);
    }

}
