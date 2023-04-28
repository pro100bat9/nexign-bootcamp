package com.example.brt;

import com.example.brt.service.BrtServiceIpml;
import com.example.commonthings.entity.Call;
import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.TypeCall;
import com.example.commonthings.model.CdrDto;
import com.example.commonthings.service.ClientService;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ActiveProfiles("integration-test")
@Testcontainers
@SpringBootTest
@Import(BrtApplicationTests.AdminClient.class)
@ContextConfiguration(initializers = {BrtApplicationTests.Initializer.class})
class BrtApplicationTests {
    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.7"))
            .withDatabaseName("Kotiki")
            .withUsername("postgres")
            .withPassword("postgres");
    @Container
    private static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));

    @MockBean
    private ClientService clientService;
    @Autowired
    private KafkaConsumer<Long, String> kafkaConsumer;
    @Autowired
    private BrtServiceIpml brtService;


    @Test
    void authorizeClient() {
        // given
        String number = "21";

        Client client = new Client();
        client.setPhoneNumber(number);
        client.setId(1L);
        client.setBalance(BigDecimal.ONE);

        Mockito.when(clientService.findClientByPhoneNumber(number)).thenReturn(client);

        // when
        brtService.authorizeClient(List.of(new CdrDto(number,
                LocalDateTime.now(),
                LocalDateTime.now(),
                new TypeCall())));

        // then
        kafkaConsumer.subscribe(List.of("sendToHrs"));
        ConsumerRecords<Long, String> poll = kafkaConsumer.poll(Duration.ofSeconds(10));
        Optional<String> cdrDto = StreamSupport.stream(poll.spliterator(), false)
                .map(ConsumerRecord::value)
                .findFirst();

        Assertions.assertAll(
                () -> Assertions.assertTrue(cdrDto.isPresent()),
                () -> Assertions.assertTrue(cdrDto.get().contains(number))
        );
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword(),
                    "kafka.server=" + kafkaContainer.getBootstrapServers()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @TestConfiguration
    static class AdminClient {
        @Bean
        public Map<String, Object> producerCallConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "hrs-service");
            return props;
        }

        @Bean
        public KafkaConsumer<Long, String> consumerCdrConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "brt-test");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            return new KafkaConsumer<>(props);
        }

        @Bean
        public ProducerFactory<Long, Call> producerLongCallFactory() {
            return new DefaultKafkaProducerFactory<>(producerCallConfigs());
        }

        @Bean
        public KafkaTemplate<Long, Call> kafkaLongCallTemplate() {
            KafkaTemplate<Long, Call> template = new KafkaTemplate<>(producerLongCallFactory());
            template.setMessageConverter(new StringJsonMessageConverter());
            return template;
        }

        @Bean
        public NewTopic sendCallToBrtTopic() {
            return TopicBuilder
                    .name("sendCallToBrt")
                    .partitions(3)
                    .build();
        }

        @Bean
        public KafkaAdmin admin() {
            return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers()));
        }
    }

}
