package com.example.commonthings.service;

import com.example.commonthings.entity.Call;
import com.example.commonthings.entity.Client;
import com.example.commonthings.entity.Payment;
import com.example.commonthings.model.CallsDetailsDto;
import com.example.commonthings.model.PaymentDto;
import com.example.commonthings.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;
    private final CallService callService;
    private final PaymentService paymentService;

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findClientByPhoneNumber(phoneNumber);
    }

    @Override
    public PaymentDto replenishmentOfTheBalance(PaymentDto payBalanceDto) {
        Client client = clientRepository.findClientByPhoneNumber(payBalanceDto.getPhoneNumber());
        BigDecimal money = new BigDecimal(payBalanceDto.getMoney());
        BigDecimal newBalance = client.getBalance().add(money);
        payBalanceDto.setId(client.getId());
        client.setBalance(newBalance);
        clientRepository.save(client);
        payBalanceDto.setMoney(newBalance.toString());
        Payment payment = new Payment(client.getId(), payBalanceDto.getPhoneNumber(), money);
        paymentService.create(payment);
        return payBalanceDto;
    }

    @Override
    public CallsDetailsDto getDetailsCalls(String phoneNumber) {
        Client client = clientRepository.findClientByPhoneNumber(phoneNumber);
        List<Call> callList = callService.getCallsByPhoneNumber(phoneNumber);
        BigDecimal totalCost = countTotalPrice(callList);
       CallsDetailsDto callsDetailsDto = new CallsDetailsDto(client.getId(), phoneNumber,
               client.getTariff().getIndex(), callList, totalCost.toString(), client.getMonetaryUnit());
        return callsDetailsDto;
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
