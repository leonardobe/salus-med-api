package med.salus.api.mapper;

import med.salus.api.domain.entity.Address;
import med.salus.api.domain.entity.Physician;
import med.salus.api.dto.request.AddressDTO;
import med.salus.api.dto.request.PhysicianInputDTO;
import med.salus.api.dto.request.PhysicianUpdateDTO;
import med.salus.api.dto.response.AddressResponseDTO;
import med.salus.api.dto.response.PhysicianListDTO;
import med.salus.api.dto.response.PhysicianResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PhysicianMapper {

    public static PhysicianListDTO toDTO(Physician physician) {
        return new PhysicianListDTO(
                physician.getId(),
                physician.getName(),
                physician.getEmail(),
                physician.getMedicalRegistration(),
                physician.getSpecialty());
    }

    public Physician toEntityFromCreateDTO(PhysicianInputDTO dto) {
        Physician physician = new Physician();

        physician.setName(dto.name());
        physician.setEmail(dto.email());
        physician.setPhone(dto.phone());
        physician.setMedicalRegistration(dto.medicalRegistration());
        physician.setSpecialty(dto.specialty());
        physician.setAddress(toAddress(dto.address()));

        return physician;
    }

    public void updateEntityFromDTO(PhysicianUpdateDTO dto, Physician physician) {

        if (dto.name() != null) {
            physician.setName(dto.name());
        }

        if (dto.phone() != null) {
            physician.setPhone(dto.phone());
        }

        if (dto.address() != null) {
            updateAddressFromDTO(dto.address(), physician.getAddress());
        }
    }

    public Address toAddress(AddressDTO dto) {
        if (dto == null) return null;

        return new Address(
                dto.street(),
                dto.number(),
                dto.complement(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.postalCode());
    }

    public void updateAddressFromDTO(AddressDTO dto, Address address) {
        if (dto == null || address == null) return;

        if (dto.street() != null) {
            address.setStreet(dto.street());
        }

        if (dto.number() != null) {
            address.setNumber(dto.number());
        }

        if (dto.complement() != null) {
            address.setComplement(dto.complement());
        }

        if (dto.neighborhood() != null) {
            address.setNeighborhood(dto.neighborhood());
        }

        if (dto.city() != null) {
            address.setCity(dto.city());
        }

        if (dto.state() != null) {
            address.setState(dto.state());
        }

        if (dto.postalCode() != null) {
            address.setPostalCode(dto.postalCode());
        }
    }

    public AddressResponseDTO toAddressResponseDTO(Address address) {
        if (address == null) return null;

        return new AddressResponseDTO(
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getPostalCode());
    }

    public PhysicianResponseDTO toResponseDTO(Physician physician) {
        return new PhysicianResponseDTO(
                physician.getId(),
                physician.getName(),
                physician.getEmail(),
                physician.getPhone(),
                physician.getMedicalRegistration(),
                physician.getSpecialty(),
                toAddressResponseDTO(physician.getAddress()));
    }
}
