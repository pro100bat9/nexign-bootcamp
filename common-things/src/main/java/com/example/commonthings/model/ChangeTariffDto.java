package com.example.commonthings.model;

import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.Tariff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChangeTariffDto {
    private String numberPhone;
    private String tariffId;

    public ChangeTariffDto(String numberPhone, String tariffId) {
        this.numberPhone = numberPhone;
        this.tariffId = tariffId;
    }
}
