package med.salus.api.mapper;

import med.salus.api.domain.entity.Address;
import med.salus.api.domain.entity.Patient;
import med.salus.api.dto.request.AddressDTO;
import med.salus.api.dto.request.PatientInputDTO;
import med.salus.api.dto.request.PatientUpdateDTO;
import med.salus.api.dto.response.AddressResponseDTO;
import med.salus.api.dto.response.PatientListDTO;
import med.salus.api.dto.response.PatientResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public static PatientListDTO toDTO(Patient patient) {
        return new PatientListDTO(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getCpf()
        );
    }

    public Patient toEntityFromCreateDTO(PatientInputDTO dto) {
        Patient patient = new Patient();

        patient.setName(dto.name());
        patient.setEmail(dto.email());
        patient.setPhone(dto.phone());
        patient.setCpf(dto.cpf());
        patient.setAddress(toAddress(dto.address()));

        return patient;
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

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getCpf(),
                toAddressResponseDTO(patient.getAddress()));
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

    public void updateEntityFromDTO(PatientUpdateDTO dto, Patient patient) {

        if (dto.name() != null) {
            patient.setName(dto.name());
        }

        if (dto.phone() != null) {
            patient.setPhone(dto.phone());
        }

        if (dto.address() != null) {
            updateAddressFromDTO(dto.address(), patient.getAddress());
        }
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
}
