package com.example.cdr.service.clientGenerator;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginAndPasswordGenerator {
    private static final Pattern LETTERS_PATTERN = Pattern.compile("[A-Za-z]+");

    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        while (sb.length() < length) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            String randomCharStr = Character.toString(randomChar);
            Matcher matcher = LETTERS_PATTERN.matcher(randomCharStr);
            if (matcher.find()) {
                sb.append(matcher.group());
            }
        }
        return sb.toString();
    }
}
