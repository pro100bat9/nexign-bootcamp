package com.example.common.tools;

import com.example.common.entity.Call;
import com.example.common.model.CdrDto;
import com.example.common.model.CdrPlusDto;
import com.example.common.model.ResultBillingDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class KafkaTemplateTool {
    private final KafkaTemplate<Long, CdrDto> kafkaCdrDtoTemplate;
    private final KafkaTemplate<Long, CdrPlusDto> producerCdrPlusDtoTemplate;
    private final KafkaTemplate<Long, ResultBillingDto> kafkaResultBillingDtoTemplate;
    private final KafkaTemplate<Long, String> kafkaStringTemplate;
    private final KafkaTemplate<Long, Call> kafkaCallTemplate;
}
