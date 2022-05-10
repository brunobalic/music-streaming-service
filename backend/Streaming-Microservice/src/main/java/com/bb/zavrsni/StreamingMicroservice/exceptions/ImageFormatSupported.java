package com.bb.zavrsni.StreamingMicroservice.exceptions;

import lombok.Getter;

public class ImageFormatSupported extends RuntimeException {

    @Getter
    private String myCustomMessage;

    public ImageFormatSupported(String message) {
        super(message);
    }

    public ImageFormatSupported(String message, String myCustomMessage) {
        super(message);
        this.myCustomMessage = myCustomMessage;
    }

}
