package com.bgr.genesis.repository;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.bgr.genesis.entity.tests.TestOrder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestOrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TestOrderRepository orderRepository;

    @Container
    public static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres");

    @Test
    void it_should_find_orders() {
        // given
        TestOrder order1 = TestOrder.builder().totalPrice(BigDecimal.TEN).build();
        TestOrder order2 = TestOrder.builder().totalPrice(BigDecimal.valueOf(2)).build();

        Object id1 = this.testEntityManager.persistAndGetId(order1);
        Object id2 =this.testEntityManager.persistAndGetId(order2);
        this.testEntityManager.flush();

        // when
        List<TestOrder> orders = this.orderRepository.findAll();

        // then
        then(orders).isNotEmpty();
        TestOrder o1 = orders.get(0);
        TestOrder o2 = orders.get(1);
        then(o1.getId()).isEqualTo(id1);
        then(o2.getId()).isEqualTo(id2);
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.datasource.url", postgresql::getJdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
    }
}