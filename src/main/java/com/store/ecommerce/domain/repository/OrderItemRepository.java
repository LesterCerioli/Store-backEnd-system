package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.OrderItem;
import java.util.List;
import java.util.Optional;


public interface OrderItemRepository {

    OrderItem save(OrderItem orderItem);

    Optional<OrderItem> findById(Long id);

    List<OrderItem> findByOrderId(Long orderId);

    void deleteById(Long id);

    boolean existsById(Long id);
}
