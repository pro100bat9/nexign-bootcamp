package com.example.common.service;

import com.example.common.entity.Call;

import java.util.List;

public interface CallService {
    Call createCall(Call call);
    void updateCall(Call call);
    List<Call> getCallsByPhoneNumber(String phoneNumber);
}
