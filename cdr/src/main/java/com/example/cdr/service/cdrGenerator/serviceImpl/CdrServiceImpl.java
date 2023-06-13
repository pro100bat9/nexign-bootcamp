package com.example.cdr.service.cdrGenerator.serviceImpl;

import com.example.cdr.service.cdrGenerator.CdrService;
import com.example.common.tools.KafkaTemplateTool;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CdrServiceImpl implements CdrService {

//    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final KafkaTemplateTool kafkaTemplateTool;

    @Override
    public void SendToBrt(){
        String message = "cdr was created";
//        kafkaTemplate.send("sendToBrt", message);
        kafkaTemplateTool.getKafkaStringTemplate().send("sendToBrt", message);

    }

}
