package com.example.cdr.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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
@AllArgsConstructor
@Slf4j
public class GeneratorCdrServiceImpl implements GeneratorCdrService{

    private final Faker faker;
    private final Random random;
    @Value("${generator.options.directoryPath}")
    private String filename;
    @Value("${generator.options.amountPhones}")
    private int amountPhone;
    private static final String stringForCdr ="%s,%s,%s,%s\n";

    private final CdrService cdrService;

    @Override
    @KafkaListener(id = "cdr", topics = {"createCdr"}, containerFactory = "singleFactory")
    public void generateCdrTxt(String message){
        File file = new File("Cdr-files/%s.txt", filename);
        StringBuilder builder = new StringBuilder();
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
                for(int i = 0; amountPhone <= i; i++){
                    generateString(builder, bufferedWriter);
                }
                bufferedWriter.write(builder.toString());

            }
            catch (IOException ex){
                ex.getStackTrace();
            }

        log.info(message);
        cdrService.SendToBrt();
    }
    public void generateString(StringBuilder builder, BufferedWriter bufferedWriter){
        String phoneNumber = generatePhoneNumber();
        int randomNumber = random.nextInt(40);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(int i = 0; i <= randomNumber; i++){
            LocalDateTime startDateTime = LocalDateTime.now();
            LocalDateTime endDateTime = generateDate(startDateTime);
            builder.append(String.format(stringForCdr, phoneNumber, generateTypeCall(),
                    startDateTime.format(dateTimeFormatter),endDateTime.format(dateTimeFormatter)));
        }

    }

    public String generatePhoneNumber(){
        String phoneNumber = "791291964" + faker.numerify("##");
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

