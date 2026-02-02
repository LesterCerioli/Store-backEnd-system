package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.OrderRequest;
import com.store.ecommerce.api.dto.OrderResponse;
import com.store.ecommerce.api.mapper.DtoMapper;
import com.store.ecommerce.application.service.CustomerService;
import com.store.ecommerce.application.service.OrderService;
import com.store.ecommerce.domain.entity.Customer;
import com.store.ecommerce.domain.entity.Order;
import com.store.ecommerce.domain.entity.OrderItem;
import com.store.ecommerce.domain.entity.Product;
import com.store.ecommerce.domain.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1/orders")
@Tag(name = "Orders", description = "Order management")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductRepository productRepository;
    private final DtoMapper mapper;

    public OrderController(OrderService orderService, CustomerService customerService,
                           ProductRepository productRepository, DtoMapper mapper) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create an order")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        Customer customer = customerService.findById(request.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + request.customerId()));
        Order order = new Order();
        order.setCustomer(customer);
        List<OrderItem> items = new ArrayList<>();
        for (var itemReq : request.items()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.productId()));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());
            items.add(item);
        }
        order.getItems().addAll(items);
        Order saved = orderService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toOrderResponse(saved));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        Order updated = orderService.updateStatus(id, orderStatus);
        return ResponseEntity.ok(mapper.toOrderResponse(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(mapper::toOrderResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all orders")
    public List<OrderResponse> list(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return orderService.findByCustomerId(customerId).stream()
                    .map(mapper::toOrderResponse)
                    .toList();
        }
        return orderService.findAll().stream()
                .map(mapper::toOrderResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
