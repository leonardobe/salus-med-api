package med.salus.api.dto.response;

import med.salus.api.domain.valueobject.Specialty;

public record PhysicianResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        String medicalRegistration,
        Specialty specialty,
        AddressResponseDTO address) {}
