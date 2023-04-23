package com.example.commonthings.service;

import com.example.commonthings.entity.Client;
import com.example.commonthings.model.CallsDetailsDto;
import com.example.commonthings.model.PaymentDto;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    Client updateClient(Client client);
    Client findClientByPhoneNumber(String phoneNumber);
    PaymentDto replenishmentOfTheBalance(PaymentDto payBalanceDto);
    CallsDetailsDto getDetailsCalls(String phoneNumber);
    List<Client> getAll();
}
