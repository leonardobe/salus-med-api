package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.ConflictException;
import med.salus.api.exception.ResourceNotFoundException;
import med.salus.api.service.PhysicianService;
import org.springframework.stereotype.Component;

@Component
public class PhysicianActiveValidator implements AppointmentSchedulingValidator {

    private final PhysicianService service;

    public PhysicianActiveValidator(PhysicianService service) {
        this.service = service;
    }

    @Override
    public void validate(AppointmentDTO dto) {

        if (dto.physicianId() == null) {
            return;
        }

        var exist = service.hasRecord(dto.physicianId());

        if (!exist) {
            throw new ResourceNotFoundException("Physician with id " + dto.patientId() + " does not exist");
        }

        var isActivePhysician = service.isActivePhysician(dto.physicianId());

        if (!isActivePhysician) {
            throw new ConflictException("Appointments cannot be scheduled with inactive physicians");
        }
    }
}
