package com.example.commonthings.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.exception.ClientNotFoundException;
import com.example.commonthings.repository.CallRepository;
import com.example.commonthings.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CallServiceImpl implements CallService{
    private final CallRepository callRepository;
    private final ClientRepository clientRepository;
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
        if(clientRepository.findClientByPhoneNumber(phoneNumber).isEmpty()){
            throw  new ClientNotFoundException("Client with phone number " + phoneNumber + " not found");
        }

        return callRepository.findAllByPhoneNumber(phoneNumber);
    }
}
