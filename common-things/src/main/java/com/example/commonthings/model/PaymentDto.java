package com.example.commonthings.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private Long id;
    private String phoneNumber;
    private String money;

    public PaymentDto(String phoneNumber, String money) {
        this.phoneNumber = phoneNumber;
        this.money = money;
    }
}
