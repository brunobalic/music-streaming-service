package com.bb.zavrsni.MusicManagementMicroservice.exceptions;

import lombok.Getter;

import java.util.function.Supplier;

public class UniversalException extends RuntimeException {

    @Getter
    private String myCustomMessage;

    public UniversalException(String message) {
        super(message);
    }

    public UniversalException(String message, String myCustomMessage) {
        super(message);
        this.myCustomMessage = myCustomMessage;
    }

}
