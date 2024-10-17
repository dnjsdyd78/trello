package com.sparta.trelloproject.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;
import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.alert.dto.AlertRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j(topic = "알림 배포")
public class SlackAlertUtil implements MessageListener {

    @Value("${SLACK_ID:null}")
    private String ID;

    @Value("${SLACK_TOKEN:null}")
    private String TOKEN;

    @Value("${SLACK_KEY:null}")
    private String KEY;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        String text = new String(message.getBody());

        try {
            if (topic.equals("liveChannel")) {
                // 실시간 알림 처리
                publishMessage(text);
            } else {
                // 토픽에러 관련 예외처리로직
                throw new ApiException(ErrorStatus._NOT_FOUND_TOPIC);
            }
        } catch (Exception e) {
            throw new ApiException(ErrorStatus._API_FAILED);
        }

    }

    private void publishMessage(String text) {

        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                    .token(TOKEN)
                    .channel(ID)
                    .text(text)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage());
            log.info("failedMessageLog: {}", text);
        }
    }
}



