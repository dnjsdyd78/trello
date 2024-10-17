package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.dto.request.CardDeleteRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.list.entity.ListEntity;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
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
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public CardSaveResponse saveCard(Long listId, CardSaveRequest cardSaveRequest) {
        // 현재 사용자에 대한 WorkspaceMember 찾기
        WorkspaceMember member = workspaceMemberRepository.findByUserId(cardSaveRequest.getUserId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);

        ListEntity listEntity = findListById(listId);

        // Card 객체 생성
        Card newCard = Card.from(cardSaveRequest, listEntity);
        // Card 객체를 저장
        cardRepository.save(newCard);
        return CardSaveResponse.of(newCard);
    }

    // 카드 + 댓글 + 매니저 조회 (모든 유저 조획 가능)
    public CardDetailResponse getCard(Long cardId) {
        Card card = cardRepository.findByIdWithDetails(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        // 카드 정보를 CardDetailResponse로 변환
        return new CardDetailResponse(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                card.getDeadLine(),
                convertCommentsToResponse(card.getComments()),
                convertManagersToResponse(card.getManagers())
        );
    }

    @Transactional
    public CardSaveResponse updateCard(Long cardId, CardUpdateRequest request) {
        // 현재 사용자에 대한 WorkspaceMember 찾기
        WorkspaceMember member = workspaceMemberRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);

        // 기존 카드 찾기
        Card existingCard = findCardById(cardId);

        // 변경 감지 방식으로 필드를 업데이트
        if (request.getTitle() != null) {
            existingCard.updateTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            existingCard.updateContent(request.getContent());
        }

        if (request.getDeadLine() != null) {
            existingCard.updateDeadLine(request.getDeadLine());
        }

        // 변경 감지가 일어나므로 save 호출은 필요 없음
        // JPA는 트랜잭션이 종료될 때 변경된 엔티티를 자동으로 flush하여 업데이트함

        // 응답 생성
        return CardSaveResponse.of(existingCard);
    }

    @Transactional
    public void deleteCard(CardDeleteRequest request) {
        cardRepository.deleteById(request.getCardId());
    }

    private Card findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }

    private ListEntity findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_ListEntity));
    }

    // 권한 체크 메서드
    public void checkPermission(WorkspaceMember member) {
        if (member.getRole() == WorkspaceMember.Role.READ_ONLY) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }
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
