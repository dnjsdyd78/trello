package com.sparta.trelloproject.common.aop;

import com.sparta.trelloproject.config.SlackAlertUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Aspect
@RequiredArgsConstructor
public class AlertAspect {

    private final SlackAlertUtil alertUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Around(("@annotation(com.sparta.trelloproject.common.annotation.SendAlert)"))
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            return joinPoint.proceed();
        } finally {
            redisTemplate.convertAndSend("liveChannel", message);
        }
    }
}