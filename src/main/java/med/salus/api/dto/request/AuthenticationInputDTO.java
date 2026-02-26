package med.salus.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "Authentication request payload")
public record AuthenticationInputDTO(
        @Schema(example = "john.doe") @NotBlank String username,
        @Schema(example = "P@ssw0rd123") @NotBlank String password) {}
