package com.bgr.genesis.repository;

import com.bgr.genesis.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_Id(Integer id);
    @Query(value = "select * from CartItem where quantity > :amount", nativeQuery = true)
    Optional<CartItem> findByQuantityGreaterThan(@Param("amount") Integer amount);
}
