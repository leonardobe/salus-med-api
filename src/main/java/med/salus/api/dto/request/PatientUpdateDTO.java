package med.salus.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "PatientUpdateRequest", description = "Request payload used to update patient information.")
public record PatientUpdateDTO(
        @Schema(description = "Unique identifier of the patient", example = "1") @NotNull
        Long id,

        @Schema(description = "Updated full name of the patient", example = "John Doe")
        String name,

        @Schema(description = "Updated phone number", example = "+55 51 98888-8888")
        String phone,

        @Schema(description = "Updated address information") AddressDTO address) {}
