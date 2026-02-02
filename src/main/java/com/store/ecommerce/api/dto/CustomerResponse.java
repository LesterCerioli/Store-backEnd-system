package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;


@Schema(description = "Customer response")
public record CustomerResponse(
        @Schema(description = "Customer ID") Long id,
        @Schema(description = "First name") String firstName,
        @Schema(description = "Last name") String lastName,
        @Schema(description = "Email") String email,
        @Schema(description = "Phone") String phone,
        @Schema(description = "Creation timestamp") Instant createdAt,
        @Schema(description = "Last update timestamp") Instant updatedAt
) {}
