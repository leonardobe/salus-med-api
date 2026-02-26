package med.salus.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.salus.api.config.security.TokenService;
import med.salus.api.domain.entity.User;
import med.salus.api.dto.request.AuthenticationInputDTO;
import med.salus.api.dto.response.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping()
    @Operation(
            summary = "Authenticate user",
            description =
                    "Authenticates a user using username and password credentials. Returns a JWT token that must be"
                            + " used in the Authorization header as a Bearer token to access protected endpoints.")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody @Valid AuthenticationInputDTO dto) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

        var authentication = manager.authenticate(authenticationToken);

        var tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(tokenJwt));
    }
}
