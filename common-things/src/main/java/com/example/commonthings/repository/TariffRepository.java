package com.example.commonthings.repository;

import com.example.commonthings.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Tariff findTariffByIndex(String Index);
}
