package com.example.commonthings.service;

import com.example.commonthings.entity.Client;
import com.example.commonthings.model.ChangeTariffDto;

public interface ManagerService {
    Client createClient(Client client);

    ChangeTariffDto changeClientTariff(ChangeTariffDto changeTariffDto);

}
