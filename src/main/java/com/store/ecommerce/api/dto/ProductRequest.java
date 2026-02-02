package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


@Schema(description = "Product create/update request")
public record ProductRequest(
        @Schema(description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wireless Mouse")
        @NotBlank(message = "Name is required")
        @Size(max = 200)
        String name,

        @Schema(description = "Product description", example = "Ergonomic wireless mouse with USB receiver")
        @Size(max = 2000)
        String description,

        @Schema(description = "Unit price in USD", requiredMode = Schema.RequiredMode.REQUIRED, example = "29.99")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be positive")
        BigDecimal price,

        @Schema(description = "Stock quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity cannot be negative")
        Integer stockQuantity,

        @Schema(description = "Stock Keeping Unit", example = "SKU-MOUSE-001")
        @Size(max = 50)
        String sku,

        @Schema(description = "Category ID")
        Long categoryId
) {}
