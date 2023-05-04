package com.example.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTariffDto {
    private Long id;
    private String numberPhone;
    private String tariffId;

    public ChangeTariffDto(Long id, String numberPhone, String tariffId) {
        this.id = id;
        this.numberPhone = numberPhone;
        this.tariffId = tariffId;
    }
}
