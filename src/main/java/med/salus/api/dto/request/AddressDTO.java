package med.salus.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "AddressRequest", description = "Address information used in patient creation and update operations.")
public record AddressDTO(
        @Schema(description = "Street name", example = "Rua Emílio Vaz") @NotBlank
        String street,

        @Schema(description = "House/building number", example = "1234")
        String number,

        @Schema(description = "Additional address details (apartment, block, etc.)", example = "Apartment 202")
        String complement,

        @Schema(description = "Neighborhood", example = "Jardim da Rainha") @NotBlank
        String neighborhood,

        @Schema(description = "City name", example = "Itapevi") @NotBlank
        String city,

        @Schema(description = "State abbreviation (2-letter code)", example = "SP") @NotBlank
        String state,

        @Schema(description = "Brazilian postal code (CEP) with 8 numeric digits", example = "06656450")
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String postalCode) {}
