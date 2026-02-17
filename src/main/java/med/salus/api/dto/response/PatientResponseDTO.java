package med.salus.api.dto.response;

public record PatientResponseDTO(
        Long id, String name, String email, String phone, String cpf, AddressResponseDTO address) {}
