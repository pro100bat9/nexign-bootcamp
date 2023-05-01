package com.example.crm.wrappers.request;

import lombok.Data;

@Data
public class DataForRegister {
    private String password;
    private String login;
    private String numberPhone;
    private String role;
}
