package com.sparta.trelloproject.common.exception;

import com.sparta.trelloproject.common.apipayload.StatusBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException{

    private final StatusBase errorCode;
}
