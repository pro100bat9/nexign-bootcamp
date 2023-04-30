package com.example.brt.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.model.CdrDto;
import com.example.commonthings.model.NumberPhoneAndBalanceDto;
import com.example.commonthings.model.ResultBillingDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface BrtService {
    void convertToDto() throws FileNotFoundException;
    void sendMessageToCdr(String message);
    void sendToCrm();
    List<NumberPhoneAndBalanceDto> getResultBillingDto();
    void calculateBalance(Call call);
    void authorizeClient(List<CdrDto> cdrDtos);
    Boolean billing();
}
