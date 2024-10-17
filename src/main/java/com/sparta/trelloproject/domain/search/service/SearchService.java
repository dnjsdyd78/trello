package com.sparta.trelloproject.domain.search.service;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.search.dto.ResultDto;
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

    private final CardRepository cardRepository;

    public ApiResponse<Page<SearchResponse>> searchCardByFilters(
            Long boardId, String titleKeyword, String bodyKeyword,
            String assigneeEmail, LocalDateTime fromDate, LocalDateTime toDate,
            int page, int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 공백데이터 허용x, null 로 재정의
        titleKeyword = nullIfEmpty(titleKeyword);
        bodyKeyword = nullIfEmpty(bodyKeyword);
        assigneeEmail = nullIfEmpty(assigneeEmail);

        // 날짜범위 유효성 검사
        checkDateRange(fromDate,toDate);

        // 결과값이랑 페이지수 반환
        ResultDto result = cardRepository.searchCardByBoardAndTitleWithContentsAndAssigneeWithinDeadlineRange
                (pageable,boardId,titleKeyword,bodyKeyword,assigneeEmail,fromDate,toDate);

        return null;
//        return ApiResponse.onSuccess(result);
    }

    // 문자열 공백이면 null 값 반환
    private String nullIfEmpty(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }

    private void checkDateRange(LocalDateTime startDate, LocalDateTime endDate){
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new ApiException(ErrorStatus._INCORRECT_DATE_RANGE);
        }
    }
}
