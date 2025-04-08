package com.example.datingtrap.exception;

import com.example.datingtrap.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), "ERROR");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
