package med.salus.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PatientDetailsResponse", description = "Detailed patient information returned by the API.")
public record PatientResponseDTO(
        @Schema(description = "Unique identifier of the patient")
        Long id,

        @Schema(description = "Full name of the patient") String name,

        @Schema(description = "Patient email address") String email,

        @Schema(description = "Patient contact phone number")
        String phone,

        @Schema(description = "Brazilian CPF") String cpf,

        @Schema(description = "Detailed address information")
        AddressResponseDTO address) {}
