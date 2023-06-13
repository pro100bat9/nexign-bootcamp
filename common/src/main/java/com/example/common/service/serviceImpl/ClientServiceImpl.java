package com.example.common.service.serviceImpl;

import com.example.common.entity.Call;
import com.example.common.entity.Client;
import com.example.common.entity.Payment;
import com.example.common.exception.ClientAlreadyExistException;
import com.example.common.exception.ClientNotFoundException;
import com.example.common.model.CallsDetailsDto;
import com.example.common.model.PaymentDto;
import com.example.common.repository.ClientRepository;
import com.example.common.service.CallService;
import com.example.common.service.ClientService;
import com.example.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CallService callService;
    private final PaymentService paymentService;

    @Override
    @CachePut(cacheNames = "client", key = "#client.phoneNumber")
    public Client createClient(Client client) {
        if(clientRepository.findClientByPhoneNumber(client.getPhoneNumber()).isPresent()){
            throw new ClientAlreadyExistException("Client with number phone " + client.getPhoneNumber() + " already exist");
        }
        return clientRepository.save(client);
    }

    @Override
    @CachePut(cacheNames = "client", key = "#client.phoneNumber")
    public Client updateClient(Client client) {
        if(clientRepository.findClientByPhoneNumber(client.getPhoneNumber()).isEmpty()){
            throw  new ClientNotFoundException("Client with phone number " + client.getPhoneNumber() + " not found");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client findClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findClientByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ClientNotFoundException("Client with phone number " + phoneNumber + " not found"));
    }

    @Override
    @CachePut(cacheNames = "client", key = "#payBalanceDto.phoneNumber")
    public PaymentDto replenishmentOfTheBalance(PaymentDto payBalanceDto) {
        var client = clientRepository.findClientByPhoneNumber(payBalanceDto.getPhoneNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException("Client with phone number " +
                                payBalanceDto.getPhoneNumber() + " not found"));
        BigDecimal money = new BigDecimal(payBalanceDto.getMoney());
        BigDecimal newBalance = client.getBalance().add(money);
        client.setBalance(newBalance);
        clientRepository.save(client);
        payBalanceDto.setMoney(newBalance.toString());
        Payment payment = Payment.builder().money(money).numberPhone(payBalanceDto.getPhoneNumber()).build();
        paymentService.create(payment);
        return payBalanceDto;
    }

    @Override
    @Cacheable(cacheNames = "client")
    public CallsDetailsDto getDetailsCalls(String phoneNumber) {
        var client = clientRepository.findClientByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ClientNotFoundException("Client with phone number " + phoneNumber + " not found"));
        List<Call> callList = callService.getCallsByPhoneNumber(phoneNumber);
        BigDecimal totalCost = countTotalPrice(callList);
        return new CallsDetailsDto(client.getId(), phoneNumber,
                client.getTariff().getIndex(), callList, totalCost.toString(), client.getMonetaryUnit());
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }


    public BigDecimal countTotalPrice(List<Call> callList){
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Call call: callList){
            totalCost = totalCost.add(call.getCost());
        }
        return totalCost;

    }


}
