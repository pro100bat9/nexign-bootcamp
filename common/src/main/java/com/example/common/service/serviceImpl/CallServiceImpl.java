package com.example.common.service.serviceImpl;

import com.example.common.entity.Call;
import com.example.common.exception.ClientNotFoundException;
import com.example.common.repository.CallRepository;
import com.example.common.repository.ClientRepository;
import com.example.common.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CallServiceImpl implements CallService {
    private final CallRepository callRepository;
    private final ClientRepository clientRepository;
    @Override
    @CachePut(cacheNames = "call", key = "#call.phoneNumber")
    public Call createCall(Call call) {
        return callRepository.save(call);
    }

    @Override
    @CachePut(cacheNames = "call", key = "#call.phoneNumber")
    public void updateCall(Call call) {
        callRepository.save(call);
    }

    @Override
    @Cacheable(cacheNames = "call")
    public List<Call> getCallsByPhoneNumber(String phoneNumber) {
        if(clientRepository.findClientByPhoneNumber(phoneNumber).isEmpty()){
            throw  new ClientNotFoundException("Client with phone number " + phoneNumber + " not found");
        }

        return callRepository.findAllByPhoneNumber(phoneNumber);
    }
}
