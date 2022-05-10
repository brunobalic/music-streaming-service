package com.bb.zavrsni.WebAppApiGateway.exceptions;

import lombok.Getter;

public class UserCreationException extends RuntimeException {

    @Getter
    private final String myCustomMessage; // mogu dodati koji god hocu field...

    public UserCreationException(String message, String myCustomMessage) {
        super(message);
        this.myCustomMessage = myCustomMessage;
    }
}
