package med.salus.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(name = "PatientCreateRequest", description = "Request payload used to register a new patient in the system.")
public record PatientInputDTO(
        @Schema(description = "Full name of the patient", example = "John Doe") @NotBlank
        String name,

        @Schema(description = "Patient email address", example = "john.doe@email.com") @NotBlank @Email
        String email,

        @Schema(description = "Patient contact phone number", example = "+55 51 99999-9999") @NotBlank
        String phone,

        @Schema(
                description = "Brazilian CPF (11 numeric digits, cannot contain repeated numbers)",
                example = "12345678901")
        @NotBlank
        @Pattern(regexp = "^(?!([0-9])\\1{10})\\d{11}$")
        String cpf,

        @Schema(description = "Patient address information") @NotNull @Valid
        AddressDTO address) {}
