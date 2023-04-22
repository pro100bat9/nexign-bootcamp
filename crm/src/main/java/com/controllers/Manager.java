package com.controllers;


import com.services.AbonentService;
import com.wrappers.request.BillingRequest;
import com.wrappers.request.ChangeTariff;
import com.wrappers.request.CreateAbonent;
import com.wrappers.response.AbonentChangeTariffResponse;
import com.wrappers.response.AbonentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/manager")
@AllArgsConstructor
public class Manager {

    private final AbonentService abonentService;

    @PatchMapping("/changeTariff")
    public ReposnseEntity<?> changeTariff(@RequestBody ChangeTariff data) {
        var client = abonentService.getClient(data.getNumberPhone());
        return ResponseEntity.ok().body(AbonentChangeTariffResponse.builder()
                .id(String.valueOf(client.getId()))
                .numberPhone(client.getPhoneNumber())
                .tariff_id(client.getTariff().getIndex())
                .build());
    }

    @PostMapping("/abonent")
    public ReposnseEntity<?> createAbonent(@RequestBody CreateAbonent data) {
        var client = abonentService.create(data.getNumberPhone(), data.getTariff_id(), data.getBalance());
        return ResponseEntity.ok().body(data);
    }

    // IMPLEMENT
    @PatchMapping("/billing")
    public ReposnseEntity<?> billing(@RequestBody BillingRequest data) {
        return ResponseEntity.ok().body();
    }
}
