package com.example.commonthings.entity;


import lombok.*;
import org.springframework.stereotype.Component;

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
    @Column(name= "id")
    private Long id;

    @Column(name= "type_call")
    private String typeCall;

    @Column(name= "phone_number", nullable = false)
    private String phoneNumber;

    public Call(String typeCall, LocalDateTime startTime, LocalDateTime endTime, double duration, BigDecimal cost,
                String phoneNumber){
        this.typeCall = typeCall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.cost = cost;
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "duration", nullable = false)
    private double duration;

    @Column(name = "cost", scale = 1)
    private BigDecimal cost;
}
