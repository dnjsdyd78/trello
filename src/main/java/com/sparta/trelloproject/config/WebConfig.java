package com.sparta.trelloproject.config;

import com.sparta.trelloproject.common.aop.AlertAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig  {

    private final SlackAlertUtil slackAlertUtil;

    @Bean
    public AlertAspect getAspect() {
        return new AlertAspect(slackAlertUtil);
    }
}
