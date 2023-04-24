package com.example.brt.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.TypeCall;
import com.example.commonthings.model.CdrPlusDto;
import com.example.commonthings.model.CdrDto;
import com.example.commonthings.model.NumberPhoneAndBalanceDto;
import com.example.commonthings.model.ResultBillingDto;
import com.example.commonthings.service.CallService;
import com.example.commonthings.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrtServiceIpml implements BrtService{

    @Value("${generator.options.directoryPath}")
    private String filename;

    private final ClientService clientService;
    private final KafkaTemplate<Long, String> kafkaStringTemplate;
    private final KafkaTemplate<Long, CdrPlusDto> kafkaListCdrPlusTemplate;
    private final CallService callService;
    private final KafkaTemplate<Long, ResultBillingDto> kafkaResultBillingDtoTemplate;



        public void authorizeClient(List<CdrDto> cdrDtos){
            List<CdrPlusDto> cdrPlusDtos = new ArrayList<>();
            for (CdrDto cdrDto : cdrDtos){
                Client client = clientService.findClientByPhoneNumber(cdrDto.getPhoneNumber());
                    if(client.getBalance().compareTo(BigDecimal.ZERO) > 0){
                        CdrPlusDto cdrPlusDto = new CdrPlusDto(cdrDto, client.getTariff());
                        cdrPlusDtos.add(cdrPlusDto);
                    }
            }
            for(CdrPlusDto cdrPlusDto: cdrPlusDtos){
                kafkaListCdrPlusTemplate.send("sendToHrs", cdrPlusDto);
            }
        }

    @KafkaListener(id = "brt", topics = {"sendCallToBrt"}, containerFactory = "singleFactory")
        public void calculateBalance(Call call){
            Client client = clientService.findClientByPhoneNumber(call.getPhoneNumber());
            BigDecimal balance = client.getBalance().subtract(call.getCost());
            client.setBalance(balance);
            clientService.updateClient(client);
            callService.createCall(call);
            sendToCrm();
        }

        public ResultBillingDto getResultBillingDto(){
            List<Client> clientList = clientService.getAll();
            ResultBillingDto resultBillingDtoList = new ResultBillingDto();
            List<NumberPhoneAndBalanceDto> numberPhoneAndBalanceDtos = new ArrayList<>();
            for (Client client : clientList){
                NumberPhoneAndBalanceDto numberPhoneAndBalanceDto =
                        new NumberPhoneAndBalanceDto(client.getPhoneNumber(), client.getBalance().toString());
                numberPhoneAndBalanceDtos.add(numberPhoneAndBalanceDto);
            }
            resultBillingDtoList.setNumbers(numberPhoneAndBalanceDtos);
            return resultBillingDtoList;
        }

        public void sendToCrm(){
            ResultBillingDto resultBillingDto = getResultBillingDto();
            kafkaResultBillingDtoTemplate.send("sendToCrmResultBillingDto", resultBillingDto);
            log.info("Result sent to crm");
        }


        public void sendMessageToCdr(){
            String message = "request to create cdr file received";
            kafkaStringTemplate.send("createCdr", message);
        }

    @KafkaListener(id = "brt", topics = {"sendToBrt"}, containerFactory = "singleFactory")
    public void convertToDto() throws FileNotFoundException {
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
            TypeCall typeCall = new TypeCall(cdrFromFile[0], typeCall(cdrFromFile[0]));
            CdrDto cdrDto = new CdrDto(cdrFromFile[1], startTime, endTime, typeCall);
            cdrDtoList.add(cdrDto);
        }

        authorizeClient(cdrDtoList);
    }

    @KafkaListener(id = "brt", topics = {"sendToBrtBilling"}, containerFactory = "singleFactory")
    public void billing(){
            sendMessageToCdr();
            log.info("Request sent to cdr");
    }

    public String typeCall(String code){
            if(code.equals("01")){
                return "Outcome";
            }
            return "Income";
    }
}
