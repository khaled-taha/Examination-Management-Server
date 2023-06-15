package com.OnlineExaminationSystem.App.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleException(ApiException exception){

        ApiExceptionResponse response =
                new ApiExceptionResponse(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
