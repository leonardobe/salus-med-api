package med.salus.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AddressResponse", description = "Address information returned by the API.")
public record AddressResponseDTO(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode) {}
