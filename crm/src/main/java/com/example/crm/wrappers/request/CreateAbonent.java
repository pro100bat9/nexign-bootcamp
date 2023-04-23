package com.example.crm.wrappers.request;

import lombok.Data;

@Data
public class CreateAbonent {
    private String numberPhone;
    private String tariff_id;
    private String balance;
}
