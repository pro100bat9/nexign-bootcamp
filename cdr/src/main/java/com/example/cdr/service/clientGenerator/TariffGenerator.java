package com.example.cdr.service.clientGenerator;

import com.example.common.entity.Tariff;
import com.example.common.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TariffGenerator {
    private final List<String> options = List.of("06", "03", "11");
    private final TariffRepository tariffRepository;


    public Tariff generateTariff(){
        int randomNumber = ThreadLocalRandom.current().nextInt(options.size());
        Optional<Tariff> tariff = tariffRepository.findTariffByIndex(options.get(randomNumber));
        if(tariff.isEmpty()){
            return tariffRepository.save(createTariff(randomNumber));
        }
        return tariff.get();
    }

    public Tariff createTariff(int index){
        if(options.get(index).equals("06")){
            return Tariff.builder()
                    .index(options.get(index))
                    .name("UnlimitedTariff")
                    .fixedPrice(BigDecimal.valueOf(100))
                    .minuteLimit(300)
                    .incomeMinuteCostBeforeLimit(BigDecimal.valueOf(0))
                    .incomeMinuteCostAfterLimit(BigDecimal.valueOf(1))
                    .outcomeMinuteCostBeforeLimit(BigDecimal.valueOf(0))
                    .outcomeMinuteCostAfterLimit(BigDecimal.valueOf(0)).build();
        }
        else if(options.get(index).equals("03")){
            return Tariff.builder()
                    .index(options.get(index))
                    .name("MinuteByMinuteTariff")
                    .fixedPrice(BigDecimal.valueOf(0))
                    .minuteLimit(0)
                    .incomeMinuteCostBeforeLimit(BigDecimal.valueOf(1.5))
                    .incomeMinuteCostAfterLimit(BigDecimal.valueOf(1.5))
                    .outcomeMinuteCostBeforeLimit(BigDecimal.valueOf(1.5))
                    .outcomeMinuteCostAfterLimit(BigDecimal.valueOf(1.5)).build();
        }
        else if(options.get(index).equals("11")){
            return Tariff.builder()
                    .index(options.get(index))
                    .name("OrdinaryTariff")
                    .fixedPrice(BigDecimal.valueOf(0))
                    .minuteLimit(100)
                    .incomeMinuteCostBeforeLimit(BigDecimal.valueOf(0))
                    .incomeMinuteCostAfterLimit(BigDecimal.valueOf(0))
                    .outcomeMinuteCostBeforeLimit(BigDecimal.valueOf(0,5))
                    .outcomeMinuteCostAfterLimit(BigDecimal.valueOf(1.5)).build();
        }
        throw new IllegalArgumentException("wrong index tariff" + index);


    }
}
