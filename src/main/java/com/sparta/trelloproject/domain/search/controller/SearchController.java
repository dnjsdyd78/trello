package com.sparta.trelloproject.domain.search.controller;

import com.sparta.trelloproject.common.apipayload.ApiResponse;
import com.sparta.trelloproject.domain.search.dto.SearchResponse;
import com.sparta.trelloproject.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
            @RequestParam String assignee,
            @RequestParam LocalDateTime fromDate,
            @RequestParam LocalDateTime toDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return searchService.searchCardByFilters(boardId,titleKeyword,bodyKeyword,assignee,fromDate,toDate,page,size);
    }
}
