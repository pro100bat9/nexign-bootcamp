package com.example.common.exception;

public class TariffNotFoundException extends RuntimeException {
    public TariffNotFoundException(String message){
        super(message);
    }
}
