package com.example.brt.service;

import com.example.commonthings.entity.Client;
import com.example.commonthings.repository.BrtRepository;
import com.example.commonthings.model.CdrDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BrtServiceIpml implements BrtService{


    private final BrtRepository brtRepository;
    private final KafkaTemplate<Long, String> kafkaTemplate;


        public void autorizeClient(CdrDto cdrDto){
            Client client = brtRepository.findClientByPhoneNumber(cdrDto.getPhoneNumber());
            int intValue = 0;
            if(client.getBalance().compareTo(BigDecimal.ZERO) > 0){

            }

        }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void sendMessageToCdr() {
            String payload = "privet";
            kafkaTemplate.send("test", payload);
    }



}
