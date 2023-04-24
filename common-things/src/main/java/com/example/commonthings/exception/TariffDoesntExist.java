package com.example.commonthings.exception;

public class TariffDoesntExist extends RuntimeException{
    public TariffDoesntExist(String message){
        super(message);
    }
}
