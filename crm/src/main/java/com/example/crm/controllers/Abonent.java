package com.example.crm.controllers;

import com.example.crm.services.AbonentServiceImpl;
import com.example.crm.wrappers.request.AbonentPay;
import com.example.crm.wrappers.response.AbonentPayResponse;
import com.example.crm.wrappers.response.AbonentReport;
import com.example.crm.wrappers.response.CallReport;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController()
@RequestMapping("/abonent")
@AllArgsConstructor
public class Abonent {

    private final AbonentServiceImpl abonentService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody AbonentPay data) {
        var client = abonentService.pay(data.getNumberPhone(), data.getMoney());
        return ResponseEntity.ok().body(AbonentPayResponse.builder()
                .id(String.valueOf(client.getId()))
                .numberPhone(client.getPhoneNumber())
                .money(client.getMoney())
                .build());
    }

    @PostMapping("/report/{numberPhone}")
    public ResponseEntity<?> report(@PathVariable String numberPhone) {
        var client = abonentService.getClient(numberPhone);
        var calls = client.getPayload();


        var callsPayload = calls.stream().map(e -> CallReport.builder()
                .callType(e.getTypeCall().toString())
                .startTime(e.getStartTime().toString())
                .endTime(e.getEndTime().toString())
                .duration((Duration.between(e.getEndTime(), e.getStartTime())).toString())
                .cost(e.getCost().toString())
                .build()).toList();


        return ResponseEntity.ok().body(AbonentReport.builder()
                .numberPhone(client.getNumberPhone())
                .id(String.valueOf(client.getId()))
                .tariffIndex(client.getTariffIndex())
                .payload(callsPayload)
                .totalCost(client.getTotalCost())
                .monetaryUnit(client.getMonetaryUnit())
                .build());
    }
}
