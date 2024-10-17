package com.sparta.trelloproject.domain.card.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.card.dto.request.CardDeleteRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/lists/{listId}/cards")
    public ApiResponse<CardSaveResponse> saveCard(@PathVariable Long listId,
                                                  @RequestBody CardSaveRequest request) {
        return ApiResponse.onSuccess(cardService.saveCard(listId, request));
    }

    // 카드 상세 조회
    @GetMapping("/cards/{cardId}")
    public ApiResponse<CardDetailResponse> getCard(@PathVariable Long cardId) {
        return ApiResponse.onSuccess(cardService.getCard(cardId));
    }

    // 카드 수정
    @PatchMapping("/cards/{cardId}")
    public ApiResponse<CardSaveResponse> updateCard(@PathVariable Long cardId,
                                                    @RequestBody CardUpdateRequest request) {
        return ApiResponse.onSuccess(cardService.updateCard(cardId, request));
    }

    // 카드 삭제
    @DeleteMapping("/cards")
    public ApiResponse<String> deleteCard(@RequestBody CardDeleteRequest request) {
        cardService.deleteCard(request);
        return ApiResponse.onSuccess("카드가 삭제되었습니다.");
    }
}
