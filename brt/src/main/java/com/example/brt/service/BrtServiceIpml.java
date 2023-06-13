package com.example.brt.service;

import com.example.common.entity.Call;
import com.example.common.entity.Client;
import com.example.common.model.CdrPlusDto;
import com.example.common.model.CdrDto;
import com.example.common.model.NumberPhoneAndBalanceDto;
import com.example.common.model.ResultBillingDto;
import com.example.common.service.CallService;
import com.example.common.service.ClientService;
import com.example.common.tools.KafkaTemplateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
//    private final KafkaTemplate<Long, String> kafkaStringTemplate;
//    private final KafkaTemplate<Long, CdrPlusDto> kafkaListCdrPlusTemplate;
//    private final KafkaTemplate<Long, CdrDto> kafkaCdrDtoTemplate;
    private final CallService callService;
//    private final KafkaTemplate<Long, ResultBillingDto> kafkaResultBillingDtoTemplate;
    private final KafkaTemplateTool kafkaTemplateTool;


        @Override
        public void authorizeClient(List<CdrDto> cdrDtos){
            List<CdrPlusDto> cdrPlusDtos = new ArrayList<>();
            for (CdrDto cdrDto : cdrDtos){
                Client client = clientService.findClientByPhoneNumber(cdrDto.getPhoneNumber());
                    if(client.getBalance().compareTo(BigDecimal.ZERO) > 0){
                        CdrPlusDto cdrPlusDto = new CdrPlusDto(cdrDto, client.getTariff());
                        cdrPlusDtos.add(cdrPlusDto);
                    }
            }
            log.info("prepair for send to hrs");
            for(CdrPlusDto cdrPlusDto: cdrPlusDtos){
                kafkaTemplateTool.getProducerCdrPlusDtoTemplate().send("sendToHrs", cdrPlusDto);
//                kafkaListCdrPlusTemplate.send("sendToHrs", cdrPlusDto);
            }
        }

        @Override
        @KafkaListener(id = "brt", topics = {"sendCallToBrt"}, containerFactory = "singleFactory")
        public void calculateBalance(Call call){
            Client client = clientService.findClientByPhoneNumber(call.getPhoneNumber());
            BigDecimal balance = client.getBalance().subtract(call.getCost());
            client.setBalance(balance);
            clientService.updateClient(client);
            callService.createCall(call);
            sendToCrm();
            log.info("sended to crm from brt");
        }

        @Override
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


    @Override
    public void sendToCrm(){
            ResultBillingDto resultBillingDto = getResultBillingDto();
            kafkaTemplateTool.getKafkaResultBillingDtoTemplate().send("sendToCrmResultBillingDto", resultBillingDto);
//            kafkaResultBillingDtoTemplate.send("sendToCrmResultBillingDto", resultBillingDto);
            log.info("Result sent to crm");
        }

    @Override
        public void sendMessageToCdr(String message){
            kafkaTemplateTool.getKafkaStringTemplate().send("createCdr", message);
//            kafkaStringTemplate.send("createCdr", message);
        }

    @KafkaListener(id = "brtSecond", topics = {"sendToBrt"}, containerFactory = "singleFactory")
    @Override
    public void convertToDto() throws FileNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        List<CdrDto> cdrDtoList = new ArrayList<>();
        File file = new File(filename);
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
            if(!checkPhoneNumber(cdrDtoList, cdrDto.getPhoneNumber())) {
                kafkaTemplateTool.getKafkaCdrDtoTemplate().send("generateClientInDB", cdrDto);
//                kafkaCdrDtoTemplate.send("generateClientInDB", cdrDto);
            }
            cdrDtoList.add(cdrDto);
        }
        authorizeClient(cdrDtoList);
    }

    @KafkaListener(id = "brtThird", topics = {"sendToBrtBilling"}, containerFactory = "singleFactory")
    @Override
    public Boolean billing(){
            sendMessageToCdr("methoodBiling");
            log.info("methoodBiling");
            return true;
    }

    public Boolean checkPhoneNumber(List<CdrDto> cdrDtoList, String phoneNumber){
            for(CdrDto cdrDto : cdrDtoList){
                if(cdrDto.getPhoneNumber().equals(phoneNumber)){
                    return true;
                }
            }
            return false;

    }
}
