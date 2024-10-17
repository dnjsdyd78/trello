package com.sparta.trelloproject.domain.search.repository;


import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomRepository {
    Page<SearchResponse> searchCardByBoardAndTitleWithContentsAndAssigneeWithinDeadlineRange(
            Pageable pageable, long boardId, String title, String contents,
            String assignee, LocalDateTime fromDate, LocalDateTime toDate
    );
}
