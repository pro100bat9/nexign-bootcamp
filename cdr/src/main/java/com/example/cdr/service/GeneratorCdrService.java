package com.example.cdr.service;

import java.time.LocalDateTime;

public interface GeneratorCdrService {
    void generateCdrTxt(String message);
    String generatePhoneNumber();
    LocalDateTime generateDate(LocalDateTime now);
    String generateTypeCall();
}
