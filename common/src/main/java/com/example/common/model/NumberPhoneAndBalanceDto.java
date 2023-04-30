package com.example.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberPhoneAndBalanceDto {
    private String phoneNumber;
    private String cost;

    public NumberPhoneAndBalanceDto(String phoneNumber, String cost){
        this.phoneNumber = phoneNumber;
        this.cost = cost;
    }
}
