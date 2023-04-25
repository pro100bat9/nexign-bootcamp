package com.example.crm.services;

import com.example.commonthings.entity.Client;
import com.example.commonthings.model.CallsDetailsDto;
import com.example.commonthings.model.ChangeTariffDto;
import com.example.commonthings.model.PaymentDto;
import com.example.commonthings.model.ResultBillingDto;

public interface AbonentService {
    PaymentDto pay(String phoneNumber, String money);
    CallsDetailsDto getClient(String numberPhone);
    ChangeTariffDto changeTariff(String numberPhone, String tariff);
    Client create(String phoneNumber, String tariffId, String balance);
    ResultBillingDto billing(String message);
    void getBilling(ResultBillingDto resultBillingDto);
}
