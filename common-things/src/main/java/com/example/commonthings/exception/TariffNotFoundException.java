package com.example.commonthings.exception;

public class TariffNotFoundException extends RuntimeException {
    public TariffNotFoundException(String message){
        super(message);
    }
}
