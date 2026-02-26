package med.salus.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse", description = "Authentication response containing JWT token")
public record AuthResponseDTO(
        @Schema(description = "JWT access token. Must be sent in the Authorization header as Bearer token")
        String accessToken) {}
