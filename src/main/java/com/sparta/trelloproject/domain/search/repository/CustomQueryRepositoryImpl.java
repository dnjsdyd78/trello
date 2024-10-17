package com.sparta.trelloproject.domain.search.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CustomQueryRepositoryImpl implements CustomRepository{

    private final JPAQueryFactory q;


    @Override
    public Page<SearchResponse> searchCardByBoardAndTitleWithContentsAndAssigneeWithinDeadlineRange(
            Pageable pageable, long boardId, String title, String contents,
            String assignee, LocalDateTime fromDate, LocalDateTime toDate
    ) {
        return null;
    }
}
