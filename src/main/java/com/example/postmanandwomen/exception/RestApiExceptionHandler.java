package com.example.postmanandwomen.exception;

import com.example.postmanandwomen.dto.Error;
import com.example.postmanandwomen.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = { RequestException.class })
    public ResponseDto<Object> handleApiRequestException(RequestException e) {

        return ResponseDto.fail(
                e.getHttpStatus(),
                e.getMessage()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {

        List<Error> errors = new ArrayList<>();

        for(FieldError field : exception.getBindingResult().getFieldErrors()) {
            errors.add(new Error(HttpStatus.BAD_REQUEST, field.getDefaultMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

}
