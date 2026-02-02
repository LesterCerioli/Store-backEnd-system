package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
}
