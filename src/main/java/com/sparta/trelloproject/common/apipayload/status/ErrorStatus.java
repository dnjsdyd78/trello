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
    _SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "변환에 실패하였습니다."),
    _INCORRECT_DATE_RANGE(HttpStatus.BAD_REQUEST, "400", "날짜 범위 설정이 잘못되었습니다."),
    _NOT_FOUND_TOPIC(HttpStatus.NOT_FOUND, "404", "토픽이 존재하지 않거나 정상적이지 않습니다."),
    // 가연
    _NOT_FOUND_ListEntity(HttpStatus.NOT_FOUND, "404", "존재하지 않는 리스트입니다."),
    _NOT_FOUND_WORKSPACE_MEMBER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 멤버입니다."),
    _NOT_FOUND_MANAGER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 매니저입니다."),

    // 예지
    _NOT_COMMENT_AUTHOR(HttpStatus.FORBIDDEN, "403", "댓글 작성자만 댓글을 수정/삭제할 수 있습니다."),
    _READ_ONLY_USER(HttpStatus.FORBIDDEN, "403", "읽기 전용 역할을 가진 사용자는 댓글을 생성할 수 없습니다."),
    _INVALID_FILE_SIZE(HttpStatus.BAD_REQUEST, "400", "파일 크기가 5MB를 초과합니다."),
    _INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "400", "지원되지 않는 파일 형식입니다."),
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
