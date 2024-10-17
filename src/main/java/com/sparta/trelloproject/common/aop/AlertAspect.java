package com.sparta.trelloproject.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.config.SlackAlertUtil;
import com.sparta.trelloproject.domain.alert.dto.AlertResponse;
import com.sparta.trelloproject.domain.alert.dto.SendAlertDto;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.comment.dto.response.CommentSaveResponseDto;
import com.sparta.trelloproject.domain.workspacemember.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Aspect
public class AlertAspect {

    private final SlackAlertUtil alertUtil;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public AlertAspect(SlackAlertUtil alertUtil, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.alertUtil = alertUtil;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Around(("@annotation(com.sparta.trelloproject.common.annotation.SendAlert)"))
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();
        Object value = null;
        String title = "";
        
        if (result instanceof ApiResponse) {
            ApiResponse<?> apiResponse = (ApiResponse<?>) result;
            Object data = apiResponse.getData();
            
            // 맴버 초대
            if (data instanceof MemberResponse) {
                value = (MemberResponse) data;
                title = "새로운 맴버가 초대되었습니다.";
            }

            // 카드 생성
            if (data instanceof CardSaveResponse) {
                value = (CardSaveResponse) data;
                title = "새 카드가 생성되었습니다.";
            }

            // 댓글 생성
            if (data instanceof CommentSaveResponseDto) {
                value = (CommentSaveResponseDto) data;
                title = "댓글이 생성되었습니다.";
            }
        }

        String text = objectMapper.writeValueAsString(value);

        redisTemplate.convertAndSend("liveChannel", "title:" + title + "contents:" + text);

        return result;
    }
}