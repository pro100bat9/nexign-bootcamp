package com.example.cdr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CdrServiceImpl implements CdrService {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    @Override
    public void SendToBrt(){
        String message = "cdr was created";
        kafkaTemplate.send("sendToBrt", message);

    }

}
