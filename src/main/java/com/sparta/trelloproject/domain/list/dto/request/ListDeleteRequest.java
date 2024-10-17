package com.sparta.trelloproject.domain.list.dto.request;

import lombok.Getter;

@Getter
public class ListDeleteRequest {
    private Long userId;
    private Long listId;
}
