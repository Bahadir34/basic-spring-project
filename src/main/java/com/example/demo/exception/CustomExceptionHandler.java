package com.example.demo.exception;

import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.exception.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgumentException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(error.getField() , error.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,String> handleConflictException(ConflictException conflictException){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message" , conflictException.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleResourceNotFoundException(ResourceNotFoundException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message" , exception.getMessage());
        return errorMap;
    }
}
