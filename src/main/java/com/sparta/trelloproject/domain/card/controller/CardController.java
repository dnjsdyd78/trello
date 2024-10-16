package com.sparta.trelloproject.domain.card.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.card.service.CardService;
import com.sparta.trelloproject.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/api/lists/{listId}/cards")
    public ApiResponse<CardSaveResponse> saveCard(@AuthenticationPrincipal AuthUser authUser,
                                                  @PathVariable Long listId,
                                                  @RequestBody CardSaveRequest request) {
        return ApiResponse.onSuccess(cardService.saveCard(authUser, listId, request));
    }

    // 카드 상세 조회
    @GetMapping("/api/cards/{cardId}")
    public ApiResponse<CardDetailResponse> getCard(@PathVariable Long cardId) {
        return ApiResponse.onSuccess(cardService.getCard(cardId));
    }

    // 카드 수정
    @PatchMapping("/api/cards/{cardId}")
    public ApiResponse<CardSaveResponse> updateCard(@AuthenticationPrincipal AuthUser authUser,
                                                    @PathVariable Long cardId,
                                                    @RequestBody CardUpdateRequest request) {
        return ApiResponse.onSuccess(cardService.updateCard(authUser, cardId, request));
    }

    // 카드 삭제
    @DeleteMapping("/api/cards/{cardId}")
    public ApiResponse<String> deleteCard(@AuthenticationPrincipal AuthUser authUser,
                                          @PathVariable Long cardId) {
        cardService.deleteCard(authUser,  cardId);
        return ApiResponse.onSuccess("카드가 삭제되었습니다.");
    }
}
