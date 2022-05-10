package com.bb.zavrsni.WebAppApiGateway.controllers;

//@RestControllerAdvice == @ControllerAdvice + @ResponseBody
//@ResponseBody + @ResponseStatus = ResponseEntity<>

import com.bb.zavrsni.WebAppApiGateway.exceptions.JsonParsingException;
import com.bb.zavrsni.WebAppApiGateway.exceptions.UniversalException;
import com.bb.zavrsni.WebAppApiGateway.exceptions.UserCreationException;
import com.bb.zavrsni.WebAppApiGateway.models.dto.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniversalException.class)
    public ResponseEntity<ApiErrorResponse> handleUniversalException(UniversalException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiErrorResponse> handleUserCreationException(UserCreationException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(exception.getMyCustomMessage()));
    }


    @ExceptionHandler(JsonParsingException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonParsingException(JsonParsingException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(exception.getMessage()));
    }

}