package com.sparta.trelloproject.domain.list.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListUpdateRequest {
    private String title;
    private Integer sequence;
}
