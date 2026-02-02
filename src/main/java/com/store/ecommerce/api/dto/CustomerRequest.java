package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(description = "Customer create/update request")
public record CustomerRequest(
        @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED, example = "John")
        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @Schema(description = "Last name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Doe")
        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        @Schema(description = "Email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "john.doe@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @Schema(description = "Phone number", example = "+1-555-123-4567")
        @Size(max = 20)
        String phone
) {}
