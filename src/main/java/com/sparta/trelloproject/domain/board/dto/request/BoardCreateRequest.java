package com.sparta.trelloproject.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardCreateRequest {
    @NotBlank(message = "Board 제목은 필수입니다.")
    private String title;

    private String backgroundColor;
//    private Long workspaceId; // 어떤 워크스페이스에 속하는지 식별하기 위해

}
