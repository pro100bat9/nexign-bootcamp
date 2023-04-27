package com.example.commonthings.repository;

import com.example.commonthings.entity.Tariff;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findTariffByIndex(String Index);
}
