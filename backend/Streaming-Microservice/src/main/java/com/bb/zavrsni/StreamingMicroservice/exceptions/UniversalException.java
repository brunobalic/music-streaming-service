package com.bb.zavrsni.StreamingMicroservice.exceptions;

import lombok.Getter;

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
