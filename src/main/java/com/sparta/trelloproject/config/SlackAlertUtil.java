package com.sparta.trelloproject.config;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Slf4j(topic = "알림 배포")
public class SlackAlertUtil {

    @Value("${SLACK_ID:null}")
    private String ID;

    @Value("${SLACK_TOKEN:null}")
    private String TOKEN;

    @Value("${SLACK_KEY:null}")
    private String KEY;

    public void publishMessage(String text) {

        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                    .token(TOKEN)
                    .channel(ID)
                    .text(text)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    public void ChatScheduleMessage(String text, LocalDateTime alertTime) throws Exception {

            var config = new AppConfig();
            config.setSingleTeamBotToken(System.getenv(TOKEN));
            config.setSigningSecret(System.getenv(KEY));
            var app = new App(config);

            app.command("/schedule", (req, ctx) -> {
                var logger = ctx.logger;
                var scheduledTime = alertTime.atZone(ZoneId.of("Asia/Seoul"));;
                try {
                    var payload = req.getPayload();
                    var result = ctx.client().chatScheduleMessage(r -> r
                            .token(ctx.getBotToken())
                            .channel(payload.getChannelId())
                            // 메시지
                            .text(text)
                            // 예약시간
                            .postAt((int)scheduledTime.toInstant().getEpochSecond())
                    );

                    log.info("result: {}", result);
                } catch (IOException | SlackApiException e) {
                    log.error("error: {}", e.getMessage(), e);
                }
                return ctx.ack();
            });

            var server = new SlackAppServer(app);
            server.start();
    }
}

