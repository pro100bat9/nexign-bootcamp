package com.example.crm.services;

import com.example.common.entity.Client;
import com.example.common.entity.Tariff;
import com.example.common.model.*;
import com.example.common.service.ClientService;
import com.example.common.service.ManagerService;
import com.example.common.service.TariffService;
import com.example.crm.exception.BillingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbonentServiceImpl implements AbonentService{
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ClientService clientService;
    private final ManagerService managerService;
    private final TariffService tariffService;
    private ResultBillingDto resultBillingDtos = new ResultBillingDto();

    @Override
    public PaymentDto pay(String phoneNumber, String money) {
        Client client = clientService.findClientByPhoneNumber(phoneNumber);
        PaymentDto paymentDto = new PaymentDto(client.getId(),phoneNumber, money);
        return clientService.replenishmentOfTheBalance(paymentDto);
    }

    @Override
    public CallsDetailsDto getClient(String numberPhone) {
        return clientService.getDetailsCalls(numberPhone);
    }
    @Override
    public ChangeTariffDto changeTariff(String numberPhone, String tariff) {
        Client client = clientService.findClientByPhoneNumber(numberPhone);
        ChangeTariffDto changeTariffDto = new ChangeTariffDto(client.getId(), numberPhone, tariff);
        return managerService.changeClientTariff(changeTariffDto);
    }

    @Override
    public Client create(String phoneNumber, String tariffId, String balance) {
        Tariff tariff = tariffService.findByIndex(tariffId);
        Client client = Client.builder()
                .balance(new BigDecimal(balance))
                .phoneNumber(phoneNumber)
                .tariff(tariff)
                .monetaryUnit("rub")
                .totalCallTime(0.0)
                .build();
        return managerService.createClient(client);
    }

    @SneakyThrows
    @Override
    public ResultBillingDto billing(String message){
        try {
            resultBillingDtos.getNumbers().clear();
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }
        if(message.equals("run")) {
            String message1 = "billing started";
            kafkaTemplate.send("sendToBrtBilling", message1);
            log.info("send to brt");

            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if(resultBillingDtos == null){
            throw new BillingException("result equals null");
        }
        return resultBillingDtos;
    }


    @KafkaListener(id = "crm", topics = {"sendToCrmResultBillingDto"}, containerFactory = "singleFactory")
    @Override
    public void getBilling(ResultBillingDto resultBillingDto){
            this.resultBillingDtos = resultBillingDto;
    }
}
