package med.salus.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientInputDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone,

        @NotBlank @Pattern(regexp = "^(?!([0-9])\\1{10})\\d{11}$")
        String cpf,

        @NotNull @Valid AddressDTO address) {}
