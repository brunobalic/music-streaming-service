package com.bb.zavrsni.WebAppApiGateway.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private Date timestamp;
    private String message;

    public ApiErrorResponse(String message) {
        this.timestamp = new Date();
        this.message = message;
    }
}
