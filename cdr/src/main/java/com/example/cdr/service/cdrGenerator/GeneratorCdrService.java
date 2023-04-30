package com.example.cdr.service.cdrGenerator;

import java.time.LocalDateTime;

public interface GeneratorCdrService {
    void generateCdrTxt();
    String generatePhoneNumber();
    LocalDateTime generateDate(LocalDateTime now);
    String generateTypeCall();
}
