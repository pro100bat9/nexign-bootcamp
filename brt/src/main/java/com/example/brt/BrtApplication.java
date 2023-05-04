package com.example.brt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableKafka
@ComponentScan(basePackages = {"com.example.common", "com.example.brt"})
public class BrtApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrtApplication.class, args);
    }
}
