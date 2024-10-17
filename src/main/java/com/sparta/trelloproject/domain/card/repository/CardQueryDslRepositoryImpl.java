package com.sparta.trelloproject.domain.card.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.search.dto.QSearchResponse;
import com.sparta.trelloproject.domain.search.dto.ResultDto;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import com.sparta.trelloproject.domain.workspacemember.controller.WorkspaceMemberController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Predicate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sparta.trelloproject.domain.board.entity.QBoard.board;
import static com.sparta.trelloproject.domain.user.entity.QUser.user;
import static com.sparta.trelloproject.domain.card.entity.QCard.card;
import static com.sparta.trelloproject.domain.list.entity.QListEntity.listEntity;
import static com.sparta.trelloproject.domain.manager.entity.QManager.manager;
import static com.sparta.trelloproject.domain.workspacemember.entity.QWorkspaceMember.workspaceMember;


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

    @Override
    public ResultDto searchCardByBoardAndTitleWithContentsAndAssigneeWithinDeadlineRange(
            Pageable pageable, Long boardId, String title, String contents,
            String assigneeEmail, LocalDateTime fromDate, LocalDateTime toDate
    ) {
        List<SearchResponse> results = queryFactory
                .select(
                        new QSearchResponse(
                                card.listEntity.board.id,
                                card
                        )
                )
                .from(card)
                .leftJoin(card.listEntity, listEntity)
                .leftJoin(listEntity.board, board)
                .leftJoin(card.managers, manager)
                .leftJoin(manager.workSpaceMember)
                .leftJoin(workspaceMember.user, user)
                .where(orIfPresentForDateRange(fromDate, toDate))
                .where(verifySearchKeywords(title, contents, assigneeEmail))
                .where(board.id.eq(boardId))
                .offset(pageable.getOffset())
                .offset(pageable.getPageSize())
                .fetch();

        // 총 데이터 카운트
        long value = queryFactory
                .select(Wildcard.count)
                .from(card)
                .leftJoin(card.listEntity, listEntity)
                .leftJoin(listEntity.board, board)
                .leftJoin(card.managers, manager)
                .leftJoin(manager.workSpaceMember)
                .leftJoin(workspaceMember.user, user)
                .where(orIfPresentForDateRange(fromDate, toDate))
                .where(verifySearchKeywords(title, contents, assigneeEmail))
                .where(board.id.eq(boardId))
                .fetchOne();

        long totalCount = Optional.of(value).orElse(0L);

        return new ResultDto(results, totalCount);
    }

    // 키워드, 닉네임 유효성 검사
    public Predicate verifySearchKeywords(String title, String contents, String assigneeEmail) {
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isBlank()) {
            builder.or(card.title.contains(title));
        }
        if (contents != null && !contents.isBlank()) {
            builder.or(card.content.contains(contents));
        }
        if (assigneeEmail != null && !assigneeEmail.isBlank()) {
            builder.or(workspaceMember.user.email.eq(assigneeEmail));
        }

        return builder;
    }

    // 날짜기간 유효성 검사
    public Predicate orIfPresentForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.or(card.deadLine.between(startDate, endDate));
        }

        return builder;
    }
}
