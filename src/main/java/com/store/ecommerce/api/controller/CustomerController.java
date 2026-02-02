package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.CustomerRequest;
import com.store.ecommerce.api.dto.CustomerResponse;
import com.store.ecommerce.api.mapper.DtoMapper;
import com.store.ecommerce.application.service.CustomerService;
import com.store.ecommerce.domain.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/customers")
@Tag(name = "Customers", description = "Customer management")
public class CustomerController {

    private final CustomerService customerService;
    private final DtoMapper mapper;

    public CustomerController(CustomerService customerService, DtoMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a customer")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        Customer entity = mapper.toCustomer(request);
        Customer saved = customerService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCustomerResponse(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {
        Customer entity = mapper.toCustomer(request);
        Customer updated = customerService.update(id, entity);
        return ResponseEntity.ok(mapper.toCustomerResponse(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(mapper::toCustomerResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all customers")
    public List<CustomerResponse> list() {
        return customerService.findAll().stream()
                .map(mapper::toCustomerResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
