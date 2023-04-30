package com.example.common.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;
    @Column(name = "balance", nullable = false, scale = 1)
    private BigDecimal balance;
    @Column(name = "total_Call_Time")
    private double totalCallTime;
    @Column(name = "monetary_unit", nullable = false)
    private String monetaryUnit;
}
