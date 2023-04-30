package com.example.common.service;

import com.example.common.entity.Tariff;
import com.example.common.exception.TariffNotFoundException;
import com.example.common.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService{
    private final TariffRepository tariffRepository;
    @Override
    public Tariff findByIndex(String index) {
        return tariffRepository.findTariffByIndex(index)
                .orElseThrow(() -> new TariffNotFoundException("tariff with index" + index + "not found"));
    }
}
