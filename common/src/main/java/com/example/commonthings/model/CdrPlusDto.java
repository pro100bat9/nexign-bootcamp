package com.example.commonthings.model;

import com.example.commonthings.entity.Tariff;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CdrPlusDto {
    CdrDto cdrDto;
    Tariff tariff;
}
