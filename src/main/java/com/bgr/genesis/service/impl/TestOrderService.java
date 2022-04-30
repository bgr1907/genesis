package com.bgr.genesis.service.impl;

import com.bgr.genesis.clients.PaymentClient;
import com.bgr.genesis.dtos.OrderDto;
import com.bgr.genesis.entity.tests.TestOrder;
import com.bgr.genesis.repository.TestOrderRepository;
import com.bgr.genesis.service.requests.CreateOrderRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestOrderService {

    private final TestOrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public OrderDto createOrder(CreateOrderRequest request) {
        BigDecimal totalPrice = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getAmount()));
        TestOrder order = TestOrder.builder().totalPrice(totalPrice).build();
        this.paymentClient.pay(order);
        TestOrder save = this.orderRepository.save(order);

        return OrderDto.builder()
            .id(save.getId())
            .totalPrice(totalPrice)
            .build();
    }
}
