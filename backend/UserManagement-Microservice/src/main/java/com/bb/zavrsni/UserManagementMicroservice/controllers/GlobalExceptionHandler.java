package com.bb.zavrsni.UserManagementMicroservice.controllers;

import com.bb.zavrsni.UserManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.UserManagementMicroservice.exceptions.UserCreationException;
import com.bb.zavrsni.UserManagementMicroservice.exceptions.UserNotFoundException;
import com.bb.zavrsni.UserManagementMicroservice.models.dto.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestControllerAdvice == @ControllerAdvice + @ResponseBody
//@ResponseBody + @ResponseStatus = ResponseEntity<>

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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

}
