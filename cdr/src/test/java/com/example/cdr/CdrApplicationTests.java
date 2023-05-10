package com.example.cdr;

import com.example.cdr.service.cdrGenerator.CdrService;
import com.example.cdr.service.cdrGenerator.GeneratorCdrService;
import com.example.cdr.service.cdrGenerator.serviceImpl.GeneratorCdrServiceImpl;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CdrApplicationTests {

    private java.time.format.DateTimeFormatter DateTimeFormatter;
    private Faker faker;
    private CdrService cdrService;

    @Test
    void testGeneratePhoneNumber() {
        GeneratorCdrService cdrGenerator = new GeneratorCdrServiceImpl(faker, new Random(), "filename", 100, cdrService);
        String phoneNumber = cdrGenerator.generatePhoneNumber();
        assertTrue(phoneNumber.matches("79129196\\d{3}"));
    }

    @Test
    void testGenerateDate() {
        GeneratorCdrService cdrGenerator = new GeneratorCdrServiceImpl(faker, new Random(), "filename", 100, cdrService);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = cdrGenerator.generateDate(now);
        assertTrue(later.isAfter(now));
    }

    @Test
    void testGenerateTypeCall() {
        GeneratorCdrService cdrGenerator = new GeneratorCdrServiceImpl(faker, new Random(), "filename", 100, cdrService);
        String typeCall = cdrGenerator.generateTypeCall();
        assertTrue(typeCall.equals("01") || typeCall.equals("02"));
    }
}
