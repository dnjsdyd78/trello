package com.sparta.trelloproject.domain.list.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.list.dto.request.ListSequenceUpdateRequest;
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
    public ApiResponse<ListSaveResponse> saveList(@PathVariable Long boardId,
                                                  @RequestBody ListSaveRequest listSaveRequest) {
        return ApiResponse.onSuccess(listService.saveList(boardId, listSaveRequest));
    }

    @PatchMapping("/lists/{listId}")
    public ApiResponse<ListSaveResponse> updateList(@PathVariable Long listId,
                                                    @RequestBody ListUpdateRequest request) {
        return ApiResponse.onSuccess(listService.updateList(listId, request));
    }

//    // 리스트 순서 변경
//    @PatchMapping("/api/lists/{listId}/sequence")
//    public ApiResponse<ListSaveResponse> updateSequenceList(@PathVariable Long listId,
//                                                    @RequestBody ListSequenceUpdateRequest request) {
//        return ApiResponse.onSuccess(listService.updateSequenceList(listId, request));
//    }

    @DeleteMapping("/lists/{listId}")
    public ApiResponse<String> deleteList(@PathVariable Long listId) {
        listService.deleteList(listId);
        return ApiResponse.onSuccess("리스트가 정상적으로 삭제되었습니다.");
    }
}

