package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.Product;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    Product create(Product product);

    Product update(Long id, Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    void deleteById(Long id);

    void updateStock(Long productId, Integer quantityChange);
}
