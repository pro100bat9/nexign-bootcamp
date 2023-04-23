package com.example.commonthings.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String index;

    private String name;

    private BigDecimal fixedPrice;

//    private BigDecimal priceForMinute;

    private Integer minuteLimit;

    private BigDecimal IncomeMinuteCostBeforeLimit;
    private BigDecimal IncomeMinuteCostAfterLimit;
    private BigDecimal OutcomeMinuteCostBeforeLimit;
    private BigDecimal OutcomeMinuteCostAfterLimit;


//    private Boolean haveLimit;
//
//    private Boolean outgoingPaid;
//    private Boolean incomingPaid;
//
//    private  Boolean limitPaid;
//
//    private BigDecimal priceAfterLimit;



}
