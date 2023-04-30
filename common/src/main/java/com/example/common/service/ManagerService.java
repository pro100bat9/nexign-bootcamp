package com.example.common.service;

import com.example.common.entity.Client;
import com.example.common.model.ChangeTariffDto;

public interface ManagerService {
    Client createClient(Client client);

    ChangeTariffDto changeClientTariff(ChangeTariffDto changeTariffDto);

}
