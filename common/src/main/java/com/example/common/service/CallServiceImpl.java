package com.example.common.service;

import com.example.common.entity.Call;
import com.example.common.exception.ClientNotFoundException;
import com.example.common.repository.CallRepository;
import com.example.common.repository.ClientRepository;
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
