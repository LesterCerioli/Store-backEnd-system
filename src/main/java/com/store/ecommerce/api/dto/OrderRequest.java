package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Schema(description = "Order create request")
public record OrderRequest(
        @Schema(description = "Customer ID", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @Schema(description = "Order line items", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "At least one item is required")
        @Valid
        List<OrderItemRequest> items
) {}
