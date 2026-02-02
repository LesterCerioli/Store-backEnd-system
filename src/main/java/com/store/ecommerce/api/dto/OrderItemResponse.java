package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;


@Schema(description = "Order item response")
public record OrderItemResponse(
        @Schema(description = "Order item ID") Long id,
        @Schema(description = "Product ID") Long productId,
        @Schema(description = "Product name") String productName,
        @Schema(description = "Quantity") Integer quantity,
        @Schema(description = "Unit price") BigDecimal unitPrice,
        @Schema(description = "Line subtotal") BigDecimal subtotal
) {}
