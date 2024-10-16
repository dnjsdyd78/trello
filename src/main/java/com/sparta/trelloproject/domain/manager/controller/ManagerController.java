package com.sparta.trelloproject.domain.manager.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.manager.dto.request.ManagerRequest;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/cards/{cardId}/managers")
    public ApiResponse<ManagerResponse> saveManager(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long cardId,
            @RequestBody ManagerRequest request) {
        ManagerResponse response = managerService.saveManager(authUser, cardId, request);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/managers/{managerId}")
    public ApiResponse<String> deleteManager(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long managerId) {
        managerService.deleteManager(authUser, managerId);
        return ApiResponse.onSuccess("매니저가 삭제되었습니다.");
    }

}
