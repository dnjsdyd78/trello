package com.sparta.trelloproject.common.apipayload.status;

import com.sparta.trelloproject.common.apipayload.StatusBase;
import com.sparta.trelloproject.common.apipayload.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements StatusBase {

    // 예시
    _NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "404", "존재하지 않는 카드입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다."),

    // 지민

    // 원용

    // 가연
    _NOT_FOUND_ListEntity(HttpStatus.NOT_FOUND, "404", "존재하지 않는 리스트입니다."),
    _NOT_FOUND_WORKSPACE_MEMBER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 멤버입니다."),
    // 예지

    // 예환
    _NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "404", "존재하지 않는 보드입니다");

    private final HttpStatus httpStatus;
    private final String statusCode;
    private final String message;

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .statusCode(statusCode)
                .message(message)
                .httpStatus(httpStatus)
                .success(false)
                .build();
    }
}
