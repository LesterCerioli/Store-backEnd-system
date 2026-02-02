package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.Product;
import java.util.List;
import java.util.Optional;


public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsBySku(String sku);
}
