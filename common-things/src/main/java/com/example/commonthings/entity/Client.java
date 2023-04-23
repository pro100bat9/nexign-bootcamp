package com.example.commonthings.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "TARIFF_ID")
    private Tariff tariff;
    private BigDecimal balance;
    private double totalCallTime;
    private String monetaryUnit;

    public Client(String phoneNumber, Tariff tariff, BigDecimal balance, double totalCallTime, String monetaryUnit) {
        this.phoneNumber = phoneNumber;
        this.tariff = tariff;
        this.balance = balance;
        this.totalCallTime = totalCallTime;
        this.monetaryUnit = monetaryUnit;
    }
}
