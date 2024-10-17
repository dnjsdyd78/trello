package com.sparta.trelloproject.domain.list.dto.response;

import com.sparta.trelloproject.domain.list.entity.ListEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListSaveResponse {
    private final Long id;
    private final String title;
    private final Integer sequence;

    public static ListSaveResponse of(ListEntity listEntity) {
        return new ListSaveResponse(
                listEntity.getListId(),
                listEntity.getTitle(),
                listEntity.getSequence()
        );
    }
}
