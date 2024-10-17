package com.sparta.trelloproject.domain.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateRequest {

    private String title;
    private String content;
    private LocalDateTime deadLine;

    private Long userId;
}
