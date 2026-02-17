package med.salus.api.dto.response;

import med.salus.api.domain.valueobject.Specialty;

public record PhysicianListDTO(Long id, String name, String email, String medicalRegistration, Specialty specialty) {}
