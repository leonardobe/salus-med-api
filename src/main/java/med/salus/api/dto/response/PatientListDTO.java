package med.salus.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PatientListResponse", description = "Summarized patient data used for paginated listings.")
public record PatientListDTO(
        @Schema(description = "Unique identifier of the patient")
        Long id,

        @Schema(description = "Full name of the patient") String name,

        @Schema(description = "Patient email address") String email,

        @Schema(description = "Brazilian CPF") String cpf) {}
