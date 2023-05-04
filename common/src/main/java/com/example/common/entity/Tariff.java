package com.example.common.entity;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "index", nullable = false, unique = true)
    private String index;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "fixed_price")
    private BigDecimal fixedPrice;
    @Column(name = "minute_limit")
    private Integer minuteLimit;

    @Column(name = "Income_Minute_Cost_Before_Limit")
    private BigDecimal incomeMinuteCostBeforeLimit;
    @Column(name = "Income_Minute_Cost_After_Limit")
    private BigDecimal incomeMinuteCostAfterLimit;
    @Column(name = "Outcome_Minute_Cost_Before_Limit")
    private BigDecimal outcomeMinuteCostBeforeLimit;
    @Column(name = "Outcome_Minute_Cost_After_Limit")
    private BigDecimal outcomeMinuteCostAfterLimit;



}
