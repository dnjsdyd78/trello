package com.sparta.trelloproject.domain.alert.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlertRequest {
    private String title;
    private String contents;
    private String assignee;
    private LocalDateTime alertTime;
}
