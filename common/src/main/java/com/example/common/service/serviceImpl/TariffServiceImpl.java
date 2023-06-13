package com.example.common.service.serviceImpl;

import com.example.common.entity.Tariff;
import com.example.common.exception.TariffNotFoundException;
import com.example.common.repository.TariffRepository;
import com.example.common.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;
    @Override
    @Cacheable(cacheNames = "tariff")
    public Tariff findByIndex(String index) {
        return tariffRepository.findTariffByIndex(index)
                .orElseThrow(() -> new TariffNotFoundException("tariff with index" + index + "not found"));
    }
}
