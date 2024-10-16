package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.common.exception.InvalidRequestException;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardQueryDslRepository;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.list.entity.ListEntity;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final ListRepository listRepository;

    @Transactional
    public CardSaveResponse saveCard(AuthUser authUser, Long listId, CardSaveRequest cardSaveRequest) {
        User user = User.fromAuthUser(authUser);

        ListEntity listEntity = findListById(listId);

        // Card 객체 생성
        Card newCard = Card.from(cardSaveRequest, listEntity);
        // Card 객체를 저장
        cardRepository.save(newCard);
        return CardSaveResponse.of(newCard);
    }

    // 카드 + 댓글 + 매니저 조회
    public CardDetailResponse getCard(Long cardId) {
        Card card = cardRepository.findByIdWithDetails(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        // 카드 정보를 CardDetailResponse로 변환
        return new CardDetailResponse(
                card.getCardId(),
                card.getTitle(),
                card.getContent(),
                card.getDeadLine(),
                convertCommentsToResponse(card.getComments()),
                convertManagersToResponse(card.getManagers())
        );
    }

    @Transactional
    public CardSaveResponse updateCard(AuthUser authUser, Long cardId, CardUpdateRequest request) {
        User user = User.fromAuthUser(authUser);

        Card existingCard = findCardById(cardId);

        // 업데이트할 필드 설정
        Card updatedCard = Card.builder()
                .title(request.getTitle() != null ? request.getTitle() : existingCard.getTitle())
                .content(request.getContent() != null ? request.getContent() : existingCard.getContent())
                .deadLine(request.getDeadLine() != null ? request.getDeadLine() : existingCard.getDeadLine())
                .listEntity(existingCard.getListEntity()) // list는 변경하지 않음
                .build();

        // 리포지토리에 저장 (옵션: JPA가 자동으로 변경사항을 감지하므로 save 호출은 생략 가능)
        cardRepository.save(updatedCard);

        // 응답 생성
        return CardSaveResponse.of(updatedCard);
    }

    @Transactional
    public void deleteCard(AuthUser authUser, Long cardId) {
        User user = User.fromAuthUser(authUser);
        cardRepository.deleteById(cardId);
    }

    private Card findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }

    private ListEntity findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_ListEntity));
    }

    // 댓글과 매니저 리스트를 DTO로 변환하는 메서드
    private List<CommentResponse> convertCommentsToResponse(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::new) // CommentResponse 생성
                .collect(Collectors.toList());
    }

    private List<ManagerResponse> convertManagersToResponse(List<Manager> managers) {
        return managers.stream()
                .map(ManagerResponse::new) // ManagerResponse 생성
                .collect(Collectors.toList());
    }

}
