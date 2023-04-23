package com.example.commonthings.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "call_type_id")
    private TypeCall typeCall;

    private String phoneNumber;

    public Call(TypeCall typeCall, LocalDateTime startTime, LocalDateTime endTime, double duration, BigDecimal cost,
                String phoneNumber){
        this.typeCall = typeCall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.cost = cost;
        this.phoneNumber = phoneNumber;
    }

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double duration;

    private BigDecimal cost;
}
