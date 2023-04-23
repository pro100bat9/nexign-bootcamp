package com.example.commonthings.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NumberPhoneAndBalanceDto {
    private String phoneNumber;
    private String cost;
}
