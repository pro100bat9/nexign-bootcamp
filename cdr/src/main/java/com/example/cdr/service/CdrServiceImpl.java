package com.example.cdr.service;

import com.example.commonthings.model.CdrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CdrServiceImpl implements CdrService {
    @Value("${generator.options.directoryPath}")
    private String filename;

    private KafkaTemplate<Long, List<CdrDto>> kafkaTemplate;

    @Autowired
    public CdrServiceImpl(KafkaTemplate<Long, List<CdrDto>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void SendToBrt() throws FileNotFoundException {
        List<CdrDto> cdrDtoList = convertToDto();
        kafkaTemplate.send("sendToBrt", cdrDtoList);

    }

    public List<CdrDto> convertToDto() throws FileNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        List<CdrDto> cdrDtoList = new ArrayList<>();
        File file = new File("Cdr-files/%s.txt", filename);
        if(!file.exists()){
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " does not exist");
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String nextLine = scanner.nextLine();
            String[] cdrFromFile = nextLine.split(",");
            LocalDateTime startTime = LocalDateTime.parse(cdrFromFile[2], formatter);
            LocalDateTime endTime = LocalDateTime.parse(cdrFromFile[3], formatter);
            CdrDto cdrDto = new CdrDto(cdrFromFile[1], startTime, endTime, cdrFromFile[0]);
            cdrDtoList.add(cdrDto);
        }
            return cdrDtoList;
    }

    @KafkaListener(id = "cdr", topics = {"test"}, containerFactory = "singleFactory")
    public void printMessageFromBrt(String message){
        System.out.println(message);
    }
}
