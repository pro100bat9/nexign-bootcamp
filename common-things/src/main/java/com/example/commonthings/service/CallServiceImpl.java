package com.example.commonthings.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.repository.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CallServiceImpl implements CallService{
    private final CallRepository callRepository;
    @Override
    public Call createCall(Call call) {
        return callRepository.save(call);
    }

    @Override
    public void updateCall(Call call) {
        callRepository.save(call);
    }

    @Override
    public List<Call> getCallsByPhoneNumber(String phoneNumber) {
        return callRepository.findAllByPhoneNumber(phoneNumber);
    }
}
