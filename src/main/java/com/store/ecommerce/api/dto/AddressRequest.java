package com.store.ecommerce.api.dto;

import com.store.ecommerce.domain.entity.Address.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "Address create/update request")
public record AddressRequest(
        @Schema(description = "Street address", requiredMode = Schema.RequiredMode.REQUIRED, example = "123 Main St")
        @NotBlank(message = "Street is required")
        @Size(max = 255)
        String street,

        @Schema(description = "City", requiredMode = Schema.RequiredMode.REQUIRED, example = "New York")
        @NotBlank(message = "City is required")
        @Size(max = 100)
        String city,

        @Schema(description = "State", requiredMode = Schema.RequiredMode.REQUIRED, example = "NY")
        @NotBlank(message = "State is required")
        @Size(max = 50)
        String state,

        @Schema(description = "ZIP code", requiredMode = Schema.RequiredMode.REQUIRED, example = "10001")
        @NotBlank(message = "ZIP code is required")
        @Size(max = 20)
        String zipCode,

        @Schema(description = "Country", requiredMode = Schema.RequiredMode.REQUIRED, example = "USA")
        @NotBlank(message = "Country is required")
        @Size(max = 100)
        String country,

        @Schema(description = "Address type: SHIPPING or BILLING", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Address type is required")
        AddressType addressType,

        @Schema(description = "Customer ID", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Customer ID is required")
        Long customerId
) {}
