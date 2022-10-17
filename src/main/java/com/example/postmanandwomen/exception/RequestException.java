package com.example.postmanandwomen.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException{
    private final HttpStatus httpStatus;


    public RequestException(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
