package com.sparta.trelloproject.domain.list.dto.response;

import com.sparta.trelloproject.domain.list.entity.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListSaveResponse {
    private final Long id;
    private final String title;
    private final int order;

    public static ListSaveResponse of(List list) {
        return new ListSaveResponse(
                list.getListId(),
                list.getTitle(),
                list.getOrder()
        );
    }
}
