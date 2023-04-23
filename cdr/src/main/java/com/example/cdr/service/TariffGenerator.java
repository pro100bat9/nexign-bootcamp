package com.example.cdr.service;

import com.example.commonthings.entity.Tariff;
import com.example.commonthings.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TariffGenerator {
    private final List<String> options = List.of("06", "03", "11");
    private final TariffRepository tariffRepository;
    public Tariff generateTariff(){
        int randomNumber = ThreadLocalRandom.current().nextInt(options.size());
        return tariffRepository.findTariffByIndex(options.get(randomNumber));
    }
}
