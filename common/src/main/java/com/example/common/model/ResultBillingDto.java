package com.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResultBillingDto {
    private List<NumberPhoneAndBalanceDto> numbers;
    public ResultBillingDto(List<NumberPhoneAndBalanceDto> numbers){
        this.numbers = numbers;
    }

}
