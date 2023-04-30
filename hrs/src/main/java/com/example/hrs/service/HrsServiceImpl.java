package com.example.hrs.service;

import com.example.common.entity.Call;
import com.example.common.entity.Client;
import com.example.common.model.CdrPlusDto;
import com.example.common.repository.ClientRepository;
import com.example.common.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class HrsServiceImpl implements HrsService{

    private final ClientRepository clientRepository;
    private final KafkaTemplate<Long, Call> kafkaTemplate;
    private final ClientService clientService;

    @KafkaListener(id = "hrs", topics = {"sendToHrs"}, containerFactory = "singleFactory")
    public void calculationBalance(CdrPlusDto cdrPlusDto){
        double duration = calculateDuration(cdrPlusDto.getCdrDto().getStartTime(),
                cdrPlusDto.getCdrDto().getEndTime());
        BigDecimal cost = calculateCost(cdrPlusDto, duration);
        Call call = new Call(cdrPlusDto.getCdrDto().getTypeCall(), cdrPlusDto.getCdrDto().getStartTime(),
                cdrPlusDto.getCdrDto().getEndTime(), duration, cost, cdrPlusDto.getCdrDto().getPhoneNumber());
        sendToBrt(call);
    }

    public void sendToBrt(Call call){
        log.info("tariff calculated");
        kafkaTemplate.send("sendCallToBrt", call);
    }

    public BigDecimal calculateCost(CdrPlusDto cdrPlusDto, double duration){
        BigDecimal result = BigDecimal.ZERO;
        Client client = clientService.findClientByPhoneNumber(cdrPlusDto.getCdrDto().getPhoneNumber());
        if(cdrPlusDto.getCdrDto().getTypeCall().equals("02")){
            result = calculatePriceForLimitIncome(cdrPlusDto, client, duration);
        }
        if(cdrPlusDto.getCdrDto().getTypeCall().equals("01")){
           result = calculatePriceForLimitOutcome(cdrPlusDto, client, duration);
        }
        double totalTime = client.getTotalCallTime() + duration;
        client.setTotalCallTime(totalTime);
        clientRepository.save(client);
        return result;
    }

    public BigDecimal calculatePriceForLimitIncome(CdrPlusDto cdrPlusDto, Client client, double duration){
        if(client.getTotalCallTime() >= cdrPlusDto.getTariff().getMinuteLimit()){
            return BigDecimal.valueOf(duration).multiply(cdrPlusDto.getTariff().getIncomeMinuteCostBeforeLimit());
        }
        else{
            return BigDecimal.valueOf(duration).multiply(cdrPlusDto.getTariff().getIncomeMinuteCostAfterLimit());
        }
    }

    public BigDecimal calculatePriceForLimitOutcome(CdrPlusDto cdrPlusDto, Client client, double duration){
        if(client.getTotalCallTime() >= cdrPlusDto.getTariff().getMinuteLimit()){
            return BigDecimal.valueOf(duration).multiply(cdrPlusDto.getTariff().getOutcomeMinuteCostBeforeLimit());
        }
        else{
            return BigDecimal.valueOf(duration).multiply(cdrPlusDto.getTariff().getOutcomeMinuteCostAfterLimit());
        }
    }



    public double calculateDuration(LocalDateTime startCall, LocalDateTime endCall){
        Duration duration = Duration.between(startCall, endCall);
        return Math.ceil(duration.getSeconds()/60 + 0.5);
    }

    }
