package com.bb.zavrsni.StreamingMicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ApiErrorResponseDto {
    private final Date timestamp;
    private final String message;

    public ApiErrorResponseDto(String message) {
        this.timestamp = new Date();
        this.message = message;
    }
}
