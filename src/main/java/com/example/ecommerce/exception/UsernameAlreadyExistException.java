package com.example.ecommerce.exception;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String message){
        super(message);
    }
}
