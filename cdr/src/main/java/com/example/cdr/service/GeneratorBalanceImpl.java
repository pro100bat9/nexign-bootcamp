package com.example.cdr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class GeneratorBalanceImpl implements GeneratorBalance {

    @Value("${cdr.generate.balance.max}")
    private int maxBalance;

    public BigDecimal generateBalance(){
        Random random = new Random();
        return new BigDecimal(ThreadLocalRandom.current().nextInt(maxBalance));
    }
}
