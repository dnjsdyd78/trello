package com.sparta.trelloproject.domain.card.dto.request;

import lombok.Getter;

@Getter
public class CardDeleteRequest {

    private Long userId;
    private Long cardId;
}
