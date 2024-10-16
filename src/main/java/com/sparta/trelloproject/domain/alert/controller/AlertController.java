package com.sparta.trelloproject.domain.alert.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.alert.dto.AlertRequest;
import com.sparta.trelloproject.domain.alert.dto.AlertResponse;
import com.sparta.trelloproject.domain.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class AlertController{

    private final AlertService alertService;

    @PostMapping("/alert")
    public ApiResponse<AlertResponse> scheduleAlert(@RequestBody AlertRequest request) {
        return ApiResponse.onSuccess(alertService.createAlert(request));
    }
}
