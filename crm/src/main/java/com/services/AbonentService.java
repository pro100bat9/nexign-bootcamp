package com.services;

import com.example.commonthings.entity.Client;
import com.example.commonthings.model.CdrDto;
import com.wrappers.response.AbonentPayResponse;
import org.aspectj.weaver.ast.Call;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonentService {

    // Вызвать кафку, вернуть иноформацию об абоненте
    public Client pay(String phoneNumber, double money) {
        return null;
    }

    public Client getClient(String numberPhone) {
        return null;
    }


    // Вернуть список звонков. НУЖНО НЕ ЗАБЫТЬ ВЕРНУТЬ СТОИМОСТЬ ЗВОНКА
    public List<CdrDto> getCallsByClient(String numberPhone) {
    }

    public Client create(String phoneNumber, String tariffId, String balance) {
        return null;
    }
}
