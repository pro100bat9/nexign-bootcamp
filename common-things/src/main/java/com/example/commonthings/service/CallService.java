package com.example.commonthings.service;

import com.example.commonthings.entity.Call;

import java.util.List;

public interface CallService {
    Call createCall(Call call);
    void updateCall(Call call);
    List<Call> getCallsByPhoneNumber(String phoneNumber);
}
