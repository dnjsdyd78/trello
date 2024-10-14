package com.sparta.trelloproject.common.apipayload.status;

import com.sparta.trelloproject.common.apipayload.StatusBase;
import com.sparta.trelloproject.common.apipayload.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements StatusBase {

    _Ok(HttpStatus.OK, "200", "Ok");

    private final HttpStatus httpStatus;
    private final String statusCode;
    private final String message;


    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .statusCode(statusCode)
                .message(message)
                .httpStatus(httpStatus)
                .success(true)
                .build();
    }
}
