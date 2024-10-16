package com.sparta.trelloproject.domain.list.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.list.dto.request.ListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trelloproject.domain.list.dto.response.ListSaveResponse;
import com.sparta.trelloproject.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ListController {

    private final ListService listService;

    @PostMapping("/boards/{boardId}/lists")
    public ApiResponse<ListSaveResponse> saveList(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId, @RequestBody ListSaveRequest listSaveRequest) {
        return ApiResponse.onSuccess(listService.saveList(authUser, boardId, listSaveRequest));
    }

    @PatchMapping("/api/boards/{boardId}/lists/{listId}")
    public ApiResponse<ListSaveResponse> updateList(@AuthenticationPrincipal AuthUser authUser,
                                                    @PathVariable Long boardId,
                                                    @PathVariable Long listId,
                                                    @RequestBody ListUpdateRequest request) {
        return ApiResponse.onSuccess(listService.updateList(authUser, boardId, listId, request));
    }

    @DeleteMapping("/api/lists/{listId}")
    public ApiResponse<String> deleteList(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId, @PathVariable Long listId) {
        listService.deleteList(authUser, listId);
        return ApiResponse.onSuccess("리스트가 정상적으로 삭제되었습니다.");
    }
}

