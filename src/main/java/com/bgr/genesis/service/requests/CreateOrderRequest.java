package com.bgr.genesis.service.requests;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private String productCode;

    private Integer amount;

    private BigDecimal unitPrice;
}
