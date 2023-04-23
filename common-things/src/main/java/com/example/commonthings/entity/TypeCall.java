package com.example.commonthings.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TypeCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public TypeCall(String code, String name){
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;
}
