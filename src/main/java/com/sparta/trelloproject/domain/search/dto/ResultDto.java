package com.sparta.trelloproject.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class ResultDto {
    private final List<SearchResponse> results;
    private final long totalCount;
}
