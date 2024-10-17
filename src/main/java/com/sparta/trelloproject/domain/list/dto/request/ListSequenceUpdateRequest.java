package com.sparta.trelloproject.domain.list.dto.request;

import lombok.Getter;

@Getter
public class ListSequenceUpdateRequest {
    private Integer sequence;

    private Long userId;
}
