package com.example.demo.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ConflictException extends RuntimeException{

    public ConflictException(String message){
        super(message);
    }
}
