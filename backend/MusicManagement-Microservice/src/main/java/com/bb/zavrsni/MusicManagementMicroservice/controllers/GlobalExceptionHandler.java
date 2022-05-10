package com.bb.zavrsni.MusicManagementMicroservice.controllers;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.FileNotValidException;
import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.MusicManagementMicroservice.models.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleFileNotValidException(FileNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(UniversalException.class)
    public ResponseEntity<ApiErrorResponseDto> handleUniversalException(UniversalException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponseDto(exception.getMessage()));
    }

}
