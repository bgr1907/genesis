package com.bgr.genesis.repository;


import com.bgr.genesis.entity.tests.TestOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestOrderRepository extends JpaRepository<TestOrder, Integer> {

}
