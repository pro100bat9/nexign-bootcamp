package com.example.brt.service;

import com.example.commonthings.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarifficationServiceImpl implements TarifficationService{
    private final ClientService clientService;
    private final BrtService brtService;



    @Override
//    @PostConstruct
    public void firstTariffication() {
        if(clientService.getAll().isEmpty()){
            if(!brtService.billing()){
                log.info("First tariffication error");
            }
            else{
                log.info("first tariffication success");
            }

        }
    }
}
