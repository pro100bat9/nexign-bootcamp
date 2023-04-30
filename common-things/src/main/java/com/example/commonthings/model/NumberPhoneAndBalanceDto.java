package com.example.commonthings.model;

import lombok.AllArgsConstructor;
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
