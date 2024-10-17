package com.sparta.trelloproject.domain.alert.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.config.SlackAlertUtil;
import com.sparta.trelloproject.domain.alert.dto.AlertRequest;
import com.sparta.trelloproject.domain.alert.dto.AlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class AlertService {

    private final SlackAlertUtil alertUtil;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public AlertResponse createAlert(AlertRequest request) {
        try {
            // 알림요청 제이슨 형태 문자열 변환
            String text = objectMapper.writeValueAsString(request);

            // 시간 데이터 Unix time 으로 변환
            LocalDateTime localDateTime = request.getAlertTime();
            long alertTime = localDateTime.toEpochSecond(ZoneOffset.UTC);

            // 레디스에 예약 메시지 저장
            redisTemplate.opsForZSet().add("scheduledMessages", text, alertTime);

            return new AlertResponse(request.getTitle(), request.getContents(), request.getAssignee(), request.getAlertTime());
        } catch (Exception e) {
            throw new ApiException(ErrorStatus._SERIALIZATION_ERROR);
        }
    }
}
