package com.example.cdr.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class AppConfig {
    @Bean
    public Faker faker() {
        return new Faker();
    }
    @Bean
    public Random random() {
        return new Random();
    }
}
