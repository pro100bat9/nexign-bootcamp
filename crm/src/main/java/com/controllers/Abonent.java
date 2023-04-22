package com.controllers;

import com.services.AbonentService;
import com.wrappers.request.AbonentPay;
import com.wrappers.response.AbonentPayResponse;
import com.wrappers.response.AbonentReport;
import com.wrappers.response.CallReport;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController()
@RequestMapping("/abonent")
@AllArgsConstructor
public class Abonent {

    private final AbonentService abonentService;

    @PostMapping("/pay")
    public ReposnseEntity<?> pay(@RequestBody AbonentPay data) {
        var client = abonentService.pay(data.getNumberPhone(), data.getMoney());
        return ResponseEntity.ok().body(AbonentPayResponse.builder()
                .id(String.valueOf(client.getId()))
                .numberPhone(client.getPhoneNumber())
                .money(client.getBalance().doubleValue())
                .build());
    }


    //
    @PostMapping("/report/{numberPhone}")
    public ResponseEntity<?> report(@PathVariable String numberPhone) {
        var client = abonentService.getClient(numberPhone);
        var calls = abonentService.getCallsByClient(numberPhone);


        var callsPayload = calls.stream().map(e -> CallReport.builder()
                .callType(e.getTypeCall().name())
                .startTime(e.getStartTime().toString())
                .endTime(e.getEndTime().toString())
                .duration((Duration.between(e.getEndTime(), e.getStartTime())).toString())
                .cost() // TODO: подумать цену звонка
                .build()).toList();


        return ResponseEntity.ok().body(AbonentReport.builder()
                .numberPhone(client.getPhoneNumber())
                .id(String.valueOf(client.getId()))
                .tariffIndex() // TODO
                .payload(callsPayload)
                .totalCost() // TODO
                .monetaryUnit() //TODO
                .build());
    }
}
