package com.example.crm.controllers;


import com.example.crm.services.AbonentService;
import com.example.crm.wrappers.request.BillingRequest;
import com.example.crm.wrappers.request.ChangeTariff;
import com.example.crm.wrappers.request.CreateAbonent;
import com.example.crm.wrappers.response.AbonentChangeTariffResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/manager")
@AllArgsConstructor
public class Manager {

    private final AbonentService abonentService;

    @PatchMapping("/changeTariff")
    public ResponseEntity<?> changeTariff(@RequestBody ChangeTariff data) {
        var client = abonentService.changeTariff(data.getNumberPhone(),data.getTariff_id());
        return ResponseEntity.ok().body(AbonentChangeTariffResponse.builder()
                .id(String.valueOf(client.getId()))
                .numberPhone(client.getNumberPhone())
                .tariff_id(client.getTariffId())
                .build());
    }

    @PostMapping("/abonent")
    public ResponseEntity<?> createAbonent(@RequestBody CreateAbonent data) {
        var client = abonentService.create(data.getNumberPhone(), data.getTariff_id(), data.getBalance());
        return ResponseEntity.ok().body(data);
    }

    @PatchMapping("/billing")
    public ResponseEntity<?> billing(@RequestBody BillingRequest data) {
       var billingResult = abonentService.billing(data.getAction());
        return ResponseEntity.ok().body(billingResult);
    }
}
