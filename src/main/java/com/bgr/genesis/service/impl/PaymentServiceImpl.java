package com.bgr.genesis.service.impl;

import com.bgr.genesis.entity.Payment;
import com.bgr.genesis.repository.PaymentRepository;
import com.bgr.genesis.service.PaymentService;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void pay(Payment payment) throws FileNotFoundException {
        this.paymentRepository.save(payment);
        log.info("======> Payment Id: {}", payment.getId());
        throw new FileNotFoundException("FileNotFoundException throws");
    }
}
