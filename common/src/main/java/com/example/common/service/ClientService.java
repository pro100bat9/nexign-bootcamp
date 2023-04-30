package com.example.common.service;

import com.example.common.entity.Client;
import com.example.common.model.CallsDetailsDto;
import com.example.common.model.PaymentDto;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    Client updateClient(Client client);
    Client findClientByPhoneNumber(String phoneNumber);
    PaymentDto replenishmentOfTheBalance(PaymentDto payBalanceDto);
    CallsDetailsDto getDetailsCalls(String phoneNumber);
    List<Client> getAll();
}
