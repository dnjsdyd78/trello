package com.sparta.trelloproject.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequest {
    @NotBlank(message = "Board 제목은 필수입니다.")
    private String title;
    private String backgroundColor;  // background로 변경 가능
}