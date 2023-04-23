package com.example.cdr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CdrServiceImpl implements CdrService {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    public CdrServiceImpl(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void SendToBrt(){
        String message = "cdr was created";
        kafkaTemplate.send("sendToBrt", message);

    }

}
