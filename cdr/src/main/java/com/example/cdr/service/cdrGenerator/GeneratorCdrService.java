package com.example.cdr.service.cdrGenerator;

import java.time.LocalDateTime;

public interface GeneratorCdrService {
    void generateCdrTxt(String message);
    String generatePhoneNumber();
    LocalDateTime generateDate(LocalDateTime now);
    String generateTypeCall();
}
