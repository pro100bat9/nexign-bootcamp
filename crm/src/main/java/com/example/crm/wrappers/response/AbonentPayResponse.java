package com.example.crm.wrappers.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbonentPayResponse {
    private String id;
    private String numberPhone;
    private String money;
}
