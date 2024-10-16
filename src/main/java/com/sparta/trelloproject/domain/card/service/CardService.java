package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.list.entity.List;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final ListRepository listRepository;

    @Transactional
    public CardSaveResponse saveCard(AuthUser authUser, Long listId, CardSaveRequest cardSaveRequest) {
        User user = User.fromAuthUser(authUser);

        List list = findListById(listId);
        Card newCard = cardRepository.save(new Card(CardSaveRequest, list));

        return CardSaveResponse.of(newCard);
    }

    public CardRepository getCard(AuthUser authUser, Long cardId) {


        return null;
    }

    @Transactional
    public CardSaveResponse updateCard(AuthUser authUser, Long cardId, CardUpdateRequest request) {
        User user = User.fromAuthUser(authUser);

        Card card = findCardById(cardId);

//        card.updateCard(request)
        return null;
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

    private List findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_List));
    }



}
