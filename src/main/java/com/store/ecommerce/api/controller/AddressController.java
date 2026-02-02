package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.AddressRequest;
import com.store.ecommerce.api.dto.AddressResponse;
import com.store.ecommerce.api.mapper.DtoMapper;
import com.store.ecommerce.application.service.AddressService;
import com.store.ecommerce.application.service.CustomerService;
import com.store.ecommerce.domain.entity.Address;
import com.store.ecommerce.domain.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/addresses")
@Tag(name = "Addresses", description = "Customer address management")
public class AddressController {

    private final AddressService addressService;
    private final CustomerService customerService;
    private final DtoMapper mapper;

    public AddressController(AddressService addressService, CustomerService customerService, DtoMapper mapper) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create an address")
    public ResponseEntity<AddressResponse> create(@Valid @RequestBody AddressRequest request) {
        Customer customer = customerService.findById(request.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + request.customerId()));
        Address entity = mapper.toAddress(request);
        entity.setCustomer(customer);
        Address saved = addressService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toAddressResponse(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address")
    public ResponseEntity<AddressResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        Address entity = mapper.toAddress(request);
        Customer customer = customerService.findById(request.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + request.customerId()));
        entity.setCustomer(customer);
        Address updated = addressService.update(id, entity);
        return ResponseEntity.ok(mapper.toAddressResponse(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID")
    public ResponseEntity<AddressResponse> getById(@PathVariable Long id) {
        return addressService.findById(id)
                .map(mapper::toAddressResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "List addresses by customer ID")
    public List<AddressResponse> listByCustomer(@PathVariable Long customerId) {
        return addressService.findByCustomerId(customerId).stream()
                .map(mapper::toAddressResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
