package med.salus.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.salus.api.domain.valueobject.Specialty;

public record PhysicianInputDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String medicalRegistration,
        @NotNull Specialty specialty,
        @NotNull @Valid AddressDTO address) {}
