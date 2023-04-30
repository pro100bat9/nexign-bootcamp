package com.example.common.model;

import com.example.common.entity.Tariff;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CdrPlusDto {
    CdrDto cdrDto;
    Tariff tariff;
}
