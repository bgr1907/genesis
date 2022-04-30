package com.bgr.genesis.service;

import com.bgr.genesis.entity.Payment;
import java.io.FileNotFoundException;

public interface PaymentService {
    void pay(Payment payment) throws FileNotFoundException;
}
