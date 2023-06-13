package com.example.common.config;

import com.example.common.entity.Call;
import com.example.common.model.CdrDto;
import com.example.common.model.CdrPlusDto;
import com.example.common.model.ResultBillingDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.producer.id}")
    private String kafkaProducerId;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    @Bean
    public ProducerFactory<Long, CdrDto> producerCdrFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, CdrDto> kafkaTemplate() {
        KafkaTemplate<Long, CdrDto> template = new KafkaTemplate<>(producerCdrFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, CdrPlusDto> producerListFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, CdrPlusDto> producerListTemplate() {
        KafkaTemplate<Long, CdrPlusDto> template = new KafkaTemplate<>(producerListFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, ResultBillingDto> producerResultBillingDtoFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, ResultBillingDto> kafkaResultBillingDtoTemplate() {
        KafkaTemplate<Long, ResultBillingDto> template = new KafkaTemplate<>(producerResultBillingDtoFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, String> producerStringFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaStringTemplate() {
        KafkaTemplate<Long, String> template = new KafkaTemplate<>(producerStringFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, Call> producerCallFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, Call> kafkaCallTemplate() {
        KafkaTemplate<Long, Call> template = new KafkaTemplate<>(producerCallFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }


    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name("sendToBrt")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder
                .name("createCdr")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder
                .name("sendToHrs")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder
                .name("sendToCrmResultBillingDto")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder
                .name("generateClientInDB")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder
                .name("sendToBrtBilling")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic6() {
        return TopicBuilder
                .name("sendCallToBrt")
                .partitions(5)
                .replicas(1)
                .build();
    }

}

