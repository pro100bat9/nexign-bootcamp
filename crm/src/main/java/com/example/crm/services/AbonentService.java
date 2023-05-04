package com.example.crm.services;

import com.example.common.entity.Client;
import com.example.common.model.*;

public interface AbonentService {
    PaymentDto pay(String phoneNumber, String money);
    CallsDetailsDto getClient(String numberPhone);
    ChangeTariffDto changeTariff(String numberPhone, String tariff);
    Client create(String phoneNumber, String tariffId, String balance);
    ResultBillingDto billing(String message);
    void getBilling(ResultBillingDto resultBillingDto);
}
