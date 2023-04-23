package com.example.commonthings.service;

import com.example.commonthings.entity.Tariff;
import com.example.commonthings.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService{
    private final TariffRepository tariffRepository;
    @Override
    public Tariff findByIndex(String index) {
        return tariffRepository.findTariffByIndex(index);
    }
}
