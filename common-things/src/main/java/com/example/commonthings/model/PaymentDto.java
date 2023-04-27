package com.example.commonthings.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private String phoneNumber;
    private String money;

    public PaymentDto(String phoneNumber, String money) {
        this.phoneNumber = phoneNumber;
        this.money = money;
    }
}
