package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;


@Schema(description = "Error response body")
public record ErrorResponse(
        @Schema(description = "Timestamp of the error") Instant timestamp,
        @Schema(description = "HTTP status code") int status,
        @Schema(description = "Error type") String error,
        @Schema(description = "Error message") String message,
        @Schema(description = "Request path") String path,
        @Schema(description = "Validation errors if any") List<String> validationErrors
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path, null);
    }

    public ErrorResponse(int status, String error, String message, String path, List<String> validationErrors) {
        this(Instant.now(), status, error, message, path, validationErrors);
    }
}
