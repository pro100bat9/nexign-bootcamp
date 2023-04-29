package com.example.cdr.service.clientGenerator;

import com.example.cdr.service.cdrGenerator.CdrServiceImpl;
import com.example.cdr.service.cdrGenerator.GeneratorCdrService;
import com.example.commonthings.entity.Client;
import com.example.commonthings.model.CdrDto;
import com.example.commonthings.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientGenerator{
    private final GeneratorCdrService generatorCdrService;
    private final GeneratorBalance generatorBalanceService;
    private final TariffGenerator tariffGenerator;
    private final ClientRepository clientRepository;

    @KafkaListener(id = "cdrSecond", topics = {"generateClientInDB"}, containerFactory = "batchFactory")
    public void generateClient(List<CdrDto> cdrDotList){
        List<Client> clientList = new ArrayList<>();
        for (CdrDto cdrDto : cdrDotList){
            Client client = Client.builder()
                    .balance(generatorBalanceService.generateBalance())
                    .phoneNumber(cdrDto.getPhoneNumber())
                    .tariff(tariffGenerator.generateTariff())
                    .build();
            clientList.add(client);
        }
        clientRepository.saveAll(clientList);
    }
}
