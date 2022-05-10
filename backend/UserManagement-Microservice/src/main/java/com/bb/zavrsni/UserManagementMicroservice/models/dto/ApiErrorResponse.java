package com.bb.zavrsni.UserManagementMicroservice.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
//@RequiredArgsConstructor // to je za ovo sto je final
public class ApiErrorResponse {
    private final Date timestamp;
    //private final int httpStatusCode; // mozda nema potrebe jer cu status ionako postaviti u responsu
    private final String message;

    public ApiErrorResponse(String message) {
        this.timestamp = new Date();
        //this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
