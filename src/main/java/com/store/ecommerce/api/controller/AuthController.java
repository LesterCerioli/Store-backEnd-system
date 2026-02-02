package com.store.ecommerce.api.controller;

import com.store.ecommerce.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "JWT token generation and validation")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    @Operation(summary = "Generate JWT token")
    public ResponseEntity<AuthService.TokenResponse> generateToken(@Valid @RequestBody TokenRequest request) {
        AuthService.TokenResponse response = authService.generateToken(
                request.clientId(),
                request.clientSecret()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token/validate")
    @Operation(summary = "Validate JWT token")
    public ResponseEntity<ValidationResponse> validateToken(@Valid @RequestBody ValidateRequest request) {
        boolean valid = authService.validateToken(request.token());
        return ResponseEntity.ok(new ValidationResponse(valid));
    }

    @GetMapping("/token")
    @Operation(summary = "Get valid token for client")
    public ResponseEntity<GetTokenResponse> getValidToken(@RequestParam String clientId) {
        return authService.getValidToken(clientId)
                .map(token -> ResponseEntity.ok(new GetTokenResponse(token)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/token/cleanup")
    @Operation(summary = "Cleanup expired tokens")
    public ResponseEntity<CleanupResponse> cleanupExpiredTokens() {
        int deleted = authService.cleanupExpiredTokens();
        return ResponseEntity.ok(new CleanupResponse(deleted));
    }

    public record TokenRequest(
            @NotBlank(message = "clientId is required") String clientId,
            @NotBlank(message = "clientSecret is required") String clientSecret
    ) {}
    public record ValidationResponse(boolean valid) {}
    public record GetTokenResponse(String token) {}
    public record CleanupResponse(int deletedCount) {}
    public record ValidateRequest(@NotBlank(message = "token is required") String token) {}
}
