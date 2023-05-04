package com.example.cdr.service.cdrGenerator.serviceImpl;

import com.example.cdr.service.cdrGenerator.CdrService;
import com.example.cdr.service.cdrGenerator.GeneratorCdrService;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class GeneratorCdrServiceImpl implements GeneratorCdrService {

    private final Faker faker;
    private final Random random;
    private String filename;
    private int amountPhone;
    private static final String stringForCdr ="%s,%s,%s,%s\n";

    private final CdrService cdrService;


    @Autowired
    public GeneratorCdrServiceImpl(Faker faker, Random random, @Value("${generator.options.directoryPath}") String filename,
                                   @Value("${generator.options.amountPhones}") int amountPhone, CdrService cdrService) {
        this.faker = faker;
        this.random = random;
        this.filename = filename;
        this.amountPhone = amountPhone;
        this.cdrService = cdrService;
    }

    @KafkaListener(id = "cdr", topics = {"createCdr"}, containerFactory = "batchFactory")
    @Override
    public void generateCdrTxt(){
        File file = new File(filename);
        StringBuilder builder = new StringBuilder();
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
                for(int i = 0; amountPhone >= i; i++){
                    generateString(builder);
                }
                bufferedWriter.write(builder.toString());
            }
            catch (IOException ex){
                ex.getStackTrace();
            }
            log.info("message");
        cdrService.SendToBrt();
    }

    public void generateString(StringBuilder builder){
        String phoneNumber = generatePhoneNumber();
        int randomNumber = random.nextInt(100);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(int i = 0; i <= randomNumber; i++){
            LocalDateTime startDateTime = LocalDateTime.now();
            LocalDateTime endDateTime = generateDate(startDateTime);
            builder.append(String.format(stringForCdr, generateTypeCall(), phoneNumber,
                    startDateTime.format(dateTimeFormatter),endDateTime.format(dateTimeFormatter)));
        }

    }

    public String generatePhoneNumber(){
        String phoneNumber = "79129196" + faker.numerify("###");
        return phoneNumber;
    }

    public LocalDateTime generateDate(LocalDateTime now){
        LocalDateTime later = now.plus(faker.random().nextInt(3600), ChronoUnit.SECONDS);
        return later;
    }
    public String generateTypeCall(){
        List<String> options = List.of("01", "02");
        return options.get(random.nextInt(2));
    }

}

