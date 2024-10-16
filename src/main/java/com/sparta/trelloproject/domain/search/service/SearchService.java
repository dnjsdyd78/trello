package com.sparta.trelloproject.domain.search.service;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SearchService {

    public ApiResponse<Page<SearchResponse>> searchCardByFilters(
            Long boardId, String titleKeyword, String bodyKeyword,
            String assignee, LocalDateTime fromDate, LocalDateTime toDate,
            int page, int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 날짜범위 유효성 검사
        checkDateRange(fromDate,toDate);

        return null;
    }


    private void checkDateRange(LocalDateTime startDate, LocalDateTime endDate){
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new ApiException(ErrorStatus._INCORRECT_DATE_RANGE);
        }
    }
}
