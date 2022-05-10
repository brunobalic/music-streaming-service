package com.bb.zavrsni.UserManagementMicroservice.exceptions;

import lombok.Getter;

public class UserCreationException extends RuntimeException {

    @Getter
    private final String myCustomMessage; // ovo samo testoram ako hocu dodat neki svoj field

    public UserCreationException(String message) {
        super(message);
        myCustomMessage = message;
        System.out.println(myCustomMessage);
    }

}
