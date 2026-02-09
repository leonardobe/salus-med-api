package med.salus.api.dto;

import jakarta.validation.constraints.NotNull;

public record PhysicianUpdateDTO(@NotNull Long id, String name, String phone, AddressDTO address) {}
