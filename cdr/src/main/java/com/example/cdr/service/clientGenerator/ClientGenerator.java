package com.example.cdr.service.clientGenerator;

import com.example.cdr.service.cdrGenerator.GeneratorCdrService;
import com.example.common.entity.Client;
import com.example.common.exception.ClientNotFoundException;
import com.example.common.model.CdrDto;
import com.example.common.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientGenerator{
    private final GeneratorCdrService generatorCdrService;
    private final GeneratorBalance generatorBalanceService;
    private final TariffGenerator tariffGenerator;
    private final ClientRepository clientRepository;

    @KafkaListener(id = "cdrSecond", topics = {"generateClientInDB"}, containerFactory = "singleFactory")
    public void generateClient(CdrDto cdrDto){
        try{
            Client clientFromDB = clientRepository.findClientByPhoneNumber(cdrDto.getPhoneNumber()).orElseThrow(
                    () -> new ClientNotFoundException("Client with phone number " + cdrDto.getPhoneNumber() + "not found"));
            clientFromDB.setBalance(generatorBalanceService.generateBalance());
            clientFromDB.setTariff(tariffGenerator.generateTariff());
            clientRepository.save(clientFromDB);
        }
        catch (ClientNotFoundException ex){
            Client client = Client.builder()
                    .balance(generatorBalanceService.generateBalance())
                    .phoneNumber(cdrDto.getPhoneNumber())
                    .tariff(tariffGenerator.generateTariff())
                    .monetaryUnit("rub")
                    .build();
            clientRepository.save(client);
        }
    }
}
