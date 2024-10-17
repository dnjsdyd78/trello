package com.sparta.trelloproject.domain.manager.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.manager.dto.request.ManagerRequest;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/cards/{cardId}/managers")
    public ApiResponse<ManagerResponse> saveManager(@PathVariable Long cardId,
                                                    @RequestBody ManagerRequest request) {
        ManagerResponse response = managerService.saveManager(cardId, request);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/managers")
    public ApiResponse<String> deleteManager(@RequestBody ManagerRequest request) {
        managerService.deleteManager(request);
        return ApiResponse.onSuccess("매니저가 삭제되었습니다.");
    }

}
