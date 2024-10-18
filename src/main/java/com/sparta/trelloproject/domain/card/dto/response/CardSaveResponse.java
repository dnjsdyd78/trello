package com.sparta.trelloproject.domain.card.dto.response;

import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@Getter
@RequiredArgsConstructor
public class CardSaveResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime deadLine;

    public static CardSaveResponse of(Card card) {
        return new CardSaveResponse(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                card.getDeadLine()
        );
    }
}
