package com.bb.zavrsni.WebAppApiGateway.exceptions;

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
