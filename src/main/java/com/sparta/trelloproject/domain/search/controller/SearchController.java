package com.sparta.trelloproject.domain.search.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import com.sparta.trelloproject.domain.search.service.SearchService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/cards")
    public ApiResponse<Page<SearchResponse>> searchCardByFilters(
            @RequestParam Long boardId,
            @RequestParam String titleKeyword,
            @RequestParam String bodyKeyword,
            @Email(message = "유효한 이메일 주소를 입력하세요") @RequestParam String assigneeEmail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime toDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return searchService.searchCardByFilters(boardId,titleKeyword,bodyKeyword,assigneeEmail,fromDate,toDate,page,size);
    }
}
