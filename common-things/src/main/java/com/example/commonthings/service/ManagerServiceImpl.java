package com.example.commonthings.service;

import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.Tariff;
import com.example.commonthings.model.ChangeTariffDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{
    private final ClientService clientService;
    private final TariffService tariffService;

    @Override
    public Client createClient(Client client) {
        Client client1 = clientService.createClient(client);
        return client1;
    }

//    TODO никинуть эксепшен на то что абонента нету
    @Override
    public ChangeTariffDto changeClientTariff(ChangeTariffDto changeTariffDto) {
        Client client = clientService.findClientByPhoneNumber(changeTariffDto.getNumberPhone());
        Tariff tariff = tariffService.findByIndex(changeTariffDto.getTariffId());
        client.setTariff(tariff);
        clientService.updateClient(client);
        changeTariffDto.setId(client.getId());
        return changeTariffDto;
    }
}
