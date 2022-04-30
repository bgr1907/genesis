package com.bgr.genesis.service;

import com.bgr.genesis.entity.Order;
import java.io.FileNotFoundException;

public interface OrderService {
    void placeOrder(Order order) throws FileNotFoundException;
}
