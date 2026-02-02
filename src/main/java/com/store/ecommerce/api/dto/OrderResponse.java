package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Schema(description = "Order response")
public record OrderResponse(
        @Schema(description = "Order ID") Long id,
        @Schema(description = "Order number") String orderNumber,
        @Schema(description = "Order status") String status,
        @Schema(description = "Total amount") BigDecimal totalAmount,
        @Schema(description = "Customer ID") Long customerId,
        @Schema(description = "Order items") List<OrderItemResponse> items,
        @Schema(description = "Creation timestamp") Instant createdAt,
        @Schema(description = "Last update timestamp") Instant updatedAt
) {}
