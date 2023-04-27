package com.example.crm.services;

import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.Tariff;
import com.example.commonthings.model.CallsDetailsDto;
import com.example.commonthings.model.ChangeTariffDto;
import com.example.commonthings.model.PaymentDto;
import com.example.commonthings.model.ResultBillingDto;
import com.example.commonthings.service.ClientService;
import com.example.commonthings.service.ManagerService;
import com.example.commonthings.service.TariffService;
import com.example.crm.exception.BillingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//TODO интерфейс сделай и по папкам раскидай
@Service
@RequiredArgsConstructor
public class AbonentServiceImpl implements AbonentService{
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ClientService clientService;
    private final ManagerService managerService;
    private final TariffService tariffService;
    private ResultBillingDto resultBillingDtos = null;

    public PaymentDto pay(String phoneNumber, String money) {
        PaymentDto paymentDto = new PaymentDto(phoneNumber, money);
        return clientService.replenishmentOfTheBalance(paymentDto);
    }

    public CallsDetailsDto getClient(String numberPhone) {
        return clientService.getDetailsCalls(numberPhone);
    }


    public ChangeTariffDto changeTariff(String numberPhone, String tariff) {
        ChangeTariffDto changeTariffDto = new ChangeTariffDto(numberPhone, tariff);
        return managerService.changeClientTariff(changeTariffDto);
    }

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

//    TODO секунды может не хватить
    @SneakyThrows
    public ResultBillingDto billing(String message){
        if(message.equals("run")) {
            String message1 = "billing started";
            kafkaTemplate.send("sendToBrtBilling", message1);
            try {
                Thread.sleep(1000);
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
    public void getBilling(ResultBillingDto resultBillingDto){
        resultBillingDtos = resultBillingDto;
    }
}
