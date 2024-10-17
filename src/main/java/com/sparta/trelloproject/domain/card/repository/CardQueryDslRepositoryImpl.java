package com.sparta.trelloproject.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sparta.trelloproject.domain.card.entity.QCard.card;
import static com.sparta.trelloproject.domain.list.entity.QListEntity.listEntity;
import static com.sparta.trelloproject.domain.manager.entity.QManager.manager;

@Repository
@RequiredArgsConstructor
public class CardQueryDslRepositoryImpl implements CardQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Card> findByIdWithDetails(Long cardId) {
        // 카드, 리스트, 매니저 조인하여 조회
        return Optional.ofNullable(
                queryFactory.select(card)
                        .from(card)
                        .leftJoin(card.listEntity, listEntity) // 카드와 리스트 조인
                        .leftJoin(card.managers, manager) // 카드와 매니저 조인
                        .where(card.id.eq(cardId))
                        .fetchOne()
        );
    }
}
