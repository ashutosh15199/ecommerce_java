package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobleExceptionHandler {
@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFoundException(UserNotFoundException ex){
    Map<String,Object> responce = new HashMap<>();
    responce.put("timestamp", LocalDateTime.now());
    responce.put("status", HttpStatus.NOT_FOUND.value());
    responce.put("error","User Not Found");
    responce.put("message",ex.getMessage());
    return new ResponseEntity<>(responce,HttpStatus.NOT_FOUND);
}

@ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Map<String,Object>> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex){
    Map<String,Object> responce = new HashMap<>();
    responce.put("timestamp",LocalDateTime.now());
    responce.put("status",HttpStatus.BAD_REQUEST.value());
    responce.put("error","User Already Exist With Same Username Try to be Another One");
    responce.put("message",ex.getMessage());
    return new ResponseEntity<>(responce,HttpStatus.BAD_REQUEST);
}

}
