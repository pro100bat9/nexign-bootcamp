package com.example.crm.controllers;


import com.example.common.exception.ClientAlreadyExistException;
import com.example.crm.services.AbonentServiceImpl;
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

    private final AbonentServiceImpl abonentService;

    @PatchMapping("/changeTariff")
    public ResponseEntity<?> changeTariff(@RequestBody ChangeTariff data) {
        var client = abonentService.changeTariff(data.getNumberPhone(),data.getTariff_id());
        return ResponseEntity.ok().body(AbonentChangeTariffResponse.builder()
                    .id(client.getId().toString())
                    .numberPhone(client.getNumberPhone())
                    .tariff_id(client.getTariffId())
                    .build());
    }

    @PostMapping("/createAbonent")
    public ResponseEntity<?> createAbonent(@RequestBody CreateAbonent data) {
        try{
            var client = abonentService.create(data.getNumberPhone(), data.getTariff_id(), data.getBalance());
        }
        catch (ClientAlreadyExistException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().body(data);
    }

    @PatchMapping("/billing")
    public ResponseEntity<?> billing(@RequestBody BillingRequest data) {
       var billingResult = abonentService.billing(data.getAction());
        return ResponseEntity.ok().body(billingResult);
    }
}
