package com.example.common.service.serviceImpl;

import com.example.common.entity.Client;
import com.example.common.entity.Tariff;
import com.example.common.model.ChangeTariffDto;
import com.example.common.service.ClientService;
import com.example.common.service.ManagerService;
import com.example.common.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {
    private final ClientService clientService;
    private final TariffService tariffService;

    @Override
    public Client createClient(Client client) {
        Client client1 = clientService.createClient(client);
        return client1;
    }


    @Override
    @CachePut(cacheNames = "client", key = "#changeTariffDto.numberPhone")
    public ChangeTariffDto changeClientTariff(ChangeTariffDto changeTariffDto) {
        var client = clientService.findClientByPhoneNumber(changeTariffDto.getNumberPhone());
        Tariff tariff = tariffService.findByIndex(changeTariffDto.getTariffId());
        client.setTariff(tariff);
        clientService.updateClient(client);
        return changeTariffDto;
    }
}
