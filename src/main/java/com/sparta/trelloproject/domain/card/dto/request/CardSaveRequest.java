package com.sparta.trelloproject.domain.card.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardSaveRequest {
    private String title;
    private String content;
    private LocalDateTime deadLine;

}
