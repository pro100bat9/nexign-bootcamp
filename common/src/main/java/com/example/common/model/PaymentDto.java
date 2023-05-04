package com.example.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private Long id;
    private String phoneNumber;
    private String money;

    public PaymentDto(Long id, String phoneNumber, String money) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.money = money;
    }
}
