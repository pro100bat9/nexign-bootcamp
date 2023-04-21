package com.example.cdr.service;

import lombok.AllArgsConstructor;
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
//@AllArgsConstructor
public class GeneratorCdrServiceImpl implements GeneratorCdrService{

    private final Faker faker;
    private final Random random;
//    @Value("${generator.options.directoryPath}")
    private String filename;
//    @Value("${generator.options.amountPhones}")
    private int amountPhone;
    private static final String stringForCdr ="%s,%s,%s,%s\n";

    @Autowired
    public GeneratorCdrServiceImpl(Faker faker, Random random, @Value("${generator.options.directoryPath}") String filename, @Value("${generator.options.amountPhones}") int amountPhone) {
        this.faker = faker;
        this.random = random;
        this.filename = filename;
        this.amountPhone = amountPhone;
    }

    @Override
//    @KafkaListener(id = "cdr", topics = {"sendToBrt"}, containerFactory = "singleFactory")
    public void generateCdrTxt(){
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


//    public List<CdrDto> generateCdrDtos(){
//        List<CdrDto> cdrDtos = new ArrayList<>();
//        int randomNumber = random.nextInt(40);
//        String phoneNumber = generatePhoneNumber();
//        for(int i = 0; i <= randomNumber; i++){
//            LocalDateTime now = LocalDateTime.now();
//                CdrDto cdrDto = new CdrDto(phoneNumber, now, generateDate(now), generateTypeCall());
//                cdrDtos.add(cdrDto);
//        }
//        return cdrDtos;
//    }

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

