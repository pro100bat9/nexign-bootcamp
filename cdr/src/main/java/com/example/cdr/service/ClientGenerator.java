package com.example.cdr.service;

import com.example.commonthings.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientGenerator implements ClientGeneratorService{
    private final GeneratorCdrService generatorCdrService;
    private final GeneratorBalance generatorBalanceService;
    private final TariffGenerator tariffGenerator;

    public Client generateClient(){
        return Client.builder()
                .balance(generatorBalanceService.generateBalance())
                .phoneNumber(generatorCdrService.generatePhoneNumber())
                .tariff(tariffGenerator.generateTariff())
                .build();
    }
}
