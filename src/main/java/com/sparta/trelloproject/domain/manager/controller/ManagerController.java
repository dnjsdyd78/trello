package com.sparta.trelloproject.domain.manager.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.manager.dto.request.ManagerSaveRequest;
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

//    @PostMapping("/cards/{cardId}/managers")
//    public ApiResponse<ManagerResponse> saveManager(
//            @AuthenticationPrincipal AuthUser authUser,
//            @RequestBody ManagerSaveRequest request) {

//        ManagerResponse response = managerService.saveManager(authUser, request);
//        return ApiResponse.onSuccess(response);
//    }

//    // 매니저 수정
//    @PatchMapping("/managers/{managerId}")
//    public ApiResponse<ManagerResponse> updateManager(
//            @AuthenticationPrincipal AuthUser authUser,
//            @PathVariable Long managerId,
//            @RequestBody ManagerUpdateRequest request) {
//
//        ManagerResponse response = managerService.updateManager(authUser, managerId, request);
//        return ApiResponse.onSuccess(response);
//    }
//
//    // 매니저 삭제
//    @DeleteMapping("/managers/{managerId}")
//    public ApiResponse<String> deleteManager(
//            @AuthenticationPrincipal AuthUser authUser,
//            @PathVariable Long managerId) {
//
//        managerService.deleteManager(authUser, managerId);
//        return ApiResponse.onSuccess("매니저가 삭제되었습니다.");
//    }

}
