package com.example.commonthings.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "TARIFF_ID")
    private Tariff tariff;
    private BigDecimal balance;
}
