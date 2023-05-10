package com.example.common.service.serviceImpl;

import com.example.common.entity.Payment;
import com.example.common.repository.PaymentRepository;
import com.example.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
}
