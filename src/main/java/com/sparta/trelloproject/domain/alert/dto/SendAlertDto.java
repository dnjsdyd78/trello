package com.sparta.trelloproject.domain.alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendAlertDto {
    private final String title;
    private final Object message;
}
