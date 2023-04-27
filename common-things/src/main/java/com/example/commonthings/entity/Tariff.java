package com.example.commonthings.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String index;

    private String name;

    private BigDecimal fixedPrice;
    private Integer minuteLimit;

    private BigDecimal IncomeMinuteCostBeforeLimit;
    private BigDecimal IncomeMinuteCostAfterLimit;
    private BigDecimal OutcomeMinuteCostBeforeLimit;
    private BigDecimal OutcomeMinuteCostAfterLimit;



}
