package com.bb.zavrsni.ArtistManagementMicroservice.controllers;

import com.bb.zavrsni.ArtistManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ApiErrorResponseDto;
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

}
