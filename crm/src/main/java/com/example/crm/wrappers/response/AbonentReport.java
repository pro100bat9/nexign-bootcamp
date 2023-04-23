package com.example.crm.wrappers.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AbonentReport {
    private String id;
    private String numberPhone;
    private String tariffIndex;
    private List<CallReport> payload;
    private String totalCost;
    private String monetaryUnit;
}
