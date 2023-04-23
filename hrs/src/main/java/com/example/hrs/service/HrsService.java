package com.example.hrs.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.model.CdrPlusDto;

import java.util.List;

public interface HrsService {
    void calculationBalance(CdrPlusDto cdrPlusDto);
}
