package com.store.ecommerce.api.dto;

import com.store.ecommerce.domain.entity.Address.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;


@Schema(description = "Address response")
public record AddressResponse(
        @Schema(description = "Address ID") Long id,
        @Schema(description = "Street") String street,
        @Schema(description = "City") String city,
        @Schema(description = "State") String state,
        @Schema(description = "ZIP code") String zipCode,
        @Schema(description = "Country") String country,
        @Schema(description = "Address type") AddressType addressType,
        @Schema(description = "Customer ID") Long customerId,
        @Schema(description = "Creation timestamp") Instant createdAt,
        @Schema(description = "Last update timestamp") Instant updatedAt
) {}
