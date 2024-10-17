package com.sparta.trelloproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SlackAlertUtil alertUtil;

    @Scheduled(fixedRate = 1000)
    public void processScheduledMessages() {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);


        Set<Object> messages = redisTemplate.opsForZSet().rangeByScore("scheduledMessages", 0, now);

        if (messages != null) {
            for (Object message : messages) {
                redisTemplate.convertAndSend("liveChannel", message);
                redisTemplate.opsForZSet().remove("scheduledMessages", message);
            }
        }
    }
}
