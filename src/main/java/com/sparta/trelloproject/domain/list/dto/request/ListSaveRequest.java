package com.sparta.trelloproject.domain.list.dto.request;

import lombok.Getter;

@Getter
public class ListSaveRequest {
    private String title;
    private int sequence;
}
