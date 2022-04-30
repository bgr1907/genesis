package com.bgr.genesis.service.impl;

import com.bgr.genesis.entity.Order;
import com.bgr.genesis.entity.Payment;
import com.bgr.genesis.repository.OrderRepository;
import com.bgr.genesis.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void placeOrder(Order order) throws FileNotFoundException {
        this.orderRepository.save(order);
        log.info("======> Order Id: {}", order.getId());
        Payment payment = Payment.builder()
            .price(order.getTotalPrice())
            .orderId(order.getId())
            .userId(order.getUserId())
            .build();
        this.paymentService.pay(payment);
    }
}
