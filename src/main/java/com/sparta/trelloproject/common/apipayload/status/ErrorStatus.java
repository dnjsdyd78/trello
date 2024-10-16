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
    _SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "텍스트 변환에 실패하였습니다."),
    _INCORRECT_DATE_RANGE(HttpStatus.BAD_REQUEST, "400", "날짜 범위 설정이 잘못되었습니다.");
    // 가연

    // 예지

    // 예환

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
