package com.bgr.genesis.services;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.bgr.genesis.clients.PaymentClient;
import com.bgr.genesis.dtos.OrderDto;
import com.bgr.genesis.entity.tests.TestOrder;
import com.bgr.genesis.repository.TestOrderRepository;

import com.bgr.genesis.service.impl.TestOrderService;
import com.bgr.genesis.service.requests.CreateOrderRequest;
import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private TestOrderService orderService;

    @Mock
    private TestOrderRepository orderRepository;

    @Mock
    private PaymentClient paymentClient;

    public static Stream<Arguments> order_requests() {
        return Stream.of(
            Arguments.of("code1", BigDecimal.valueOf(12.3), 5, BigDecimal.valueOf(61.5)),
            Arguments.of("code2", BigDecimal.valueOf(15), 10, BigDecimal.valueOf(150))
        );
    }

    @ParameterizedTest
    @MethodSource("order_requests")
    void it_should_create_orders(String productCode, BigDecimal unitPrice, Integer amount, BigDecimal totalPrice) {

        // given
        CreateOrderRequest request = CreateOrderRequest.builder()
            .productCode("code1")
            .unitPrice(unitPrice)
            .amount(amount)
            .build();

        TestOrder order = new TestOrder();
        order.setId(231231231);

        when(orderRepository.save(any())).thenReturn((order));

        //when
        OrderDto orderDto = orderService.createOrder(request);

        //then
        then(orderDto.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    void it_should_fail_order_creation_when_payment_failed() {
        // given
        CreateOrderRequest request = CreateOrderRequest.builder()
            .productCode("code1")
            .unitPrice(BigDecimal.valueOf(12))
            .amount(3)
            .build();

        doThrow(new IllegalArgumentException()).when(paymentClient).pay(any());

        // when
        Throwable throwable = catchThrowable(() -> {
            orderService.createOrder(request);
        });

        // then
        then(throwable).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(orderRepository);
    }
}
