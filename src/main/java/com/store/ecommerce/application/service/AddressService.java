package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.Address;
import java.util.List;
import java.util.Optional;


public interface AddressService {

    Address create(Address address);

    Address update(Long id, Address address);

    Optional<Address> findById(Long id);

    List<Address> findByCustomerId(Long customerId);

    void deleteById(Long id);
}
