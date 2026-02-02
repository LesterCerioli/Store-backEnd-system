package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

    Customer create(Customer customer);

    Customer update(Long id, Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    void deleteById(Long id);
}
