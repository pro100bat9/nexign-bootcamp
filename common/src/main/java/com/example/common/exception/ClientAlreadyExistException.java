package com.example.common.exception;

public class ClientAlreadyExistException extends RuntimeException{
    public ClientAlreadyExistException(String message){
        super(message);
    }
}
