package com.sparta.trelloproject.domain.alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class AlertResponse {
    private String alertTitle;
    private String alertContents;
    private String assignee;
    private LocalDateTime alertTime;
}
