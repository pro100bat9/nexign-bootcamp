package com.example.crm.controllers;

import com.example.common.entity.Role;
import com.example.common.service.UserService;
import com.example.crm.services.serviceImpl.AbonentServiceImpl;
import com.example.crm.wrappers.request.AbonentPay;
import com.example.crm.wrappers.response.AbonentPayResponse;
import com.example.crm.wrappers.response.AbonentReport;
import com.example.crm.wrappers.response.CallReport;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController()
@RequestMapping("/abonent")
@AllArgsConstructor
public class Abonent {

    private final AbonentServiceImpl abonentService;
    private final UserService userService;

    @PatchMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody AbonentPay data) {
        var client = abonentService.pay(data.getNumberPhone(), data.getMoney());
        return ResponseEntity.ok().body(AbonentPayResponse.builder()
                .id(String.valueOf(client.getId()))
                .numberPhone(client.getPhoneNumber())
                .money(client.getMoney())
                .build());
    }

    @GetMapping("/report/{numberPhone}")
    public ResponseEntity<?> report(@PathVariable String numberPhone) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = abonentService.getClient(numberPhone);
        var user = userService.getUserByLogin(authentication.getName());
        if(!user.getPhoneNumber().equals(client.getNumberPhone()) && user.getRole() != Role.ROLE_MANAGER){
            return new ResponseEntity<>("you can't see calls from other customers", HttpStatus.FORBIDDEN);
        }
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
