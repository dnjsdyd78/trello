package com.sparta.trelloproject.common.apipayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sparta.trelloproject.common.apipayload.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "statusCode", "message", "data"})
public class ApiResponse<T> {

    private final Boolean success;

    private final String statusCode;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(true, SuccessStatus._Ok.getStatusCode(), SuccessStatus._Ok.getMessage(), data);
    }

    public static ApiResponse<String> onFailure(StatusBase errorCode){
        return new ApiResponse<>(false, errorCode.getReasonHttpStatus().getStatusCode(), errorCode.getReasonHttpStatus().getMessage(), "null");
    }
}
