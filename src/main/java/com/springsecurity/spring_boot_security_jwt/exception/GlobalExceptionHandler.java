package com.springsecurity.spring_boot_security_jwt.exception;

import com.springsecurity.spring_boot_security_jwt.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler( ResourceNotFoundException.class )
    public ResponseEntity< ApiResponse< ? > > resourceNotFoundExceptionHandler (ResourceNotFoundException ex) {

        ApiResponse< ? > response = ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .code(HttpStatus.NOT_FOUND.value())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

