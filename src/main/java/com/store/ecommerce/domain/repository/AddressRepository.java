package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.Address;
import java.util.List;
import java.util.Optional;


public interface AddressRepository {

    Address save(Address address);

    Optional<Address> findById(Long id);

    List<Address> findByCustomerId(Long customerId);

    void deleteById(Long id);

    boolean existsById(Long id);
}
