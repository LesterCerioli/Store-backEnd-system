package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.Order;
import java.util.List;
import java.util.Optional;


public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAll();

    List<Order> findByCustomerId(Long customerId);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByOrderNumber(String orderNumber);
}
