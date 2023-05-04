package com.example.brt.service;

import com.example.common.entity.Call;
import com.example.common.model.CdrDto;
import com.example.common.model.NumberPhoneAndBalanceDto;
import com.example.common.model.ResultBillingDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface BrtService {
    void convertToDto() throws FileNotFoundException;
    void sendMessageToCdr(String message);
    void sendToCrm();
    ResultBillingDto getResultBillingDto();
    void calculateBalance(Call call);
    void authorizeClient(List<CdrDto> cdrDtos);
    Boolean billing();
}
