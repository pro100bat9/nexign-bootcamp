package com.example.cdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.common", "com.example.cdr"})
@EnableKafka
public class CdrApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdrApplication.class, args);
    }

}
