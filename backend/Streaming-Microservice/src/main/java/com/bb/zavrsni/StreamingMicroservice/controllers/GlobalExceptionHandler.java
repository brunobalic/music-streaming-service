package com.bb.zavrsni.StreamingMicroservice.controllers;

import com.bb.zavrsni.StreamingMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.StreamingMicroservice.models.ApiErrorResponseDto;
import com.bb.zavrsni.StreamingMicroservice.exceptions.ImageFormatSupported;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniversalException.class)
    public ResponseEntity<ApiErrorResponseDto> handleUniversalException(UniversalException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(ImageFormatSupported.class)
    public ResponseEntity<ApiErrorResponseDto> handleImageTypeNotSupported(ImageFormatSupported exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponseDto(exception.getMessage()));
    }

}
