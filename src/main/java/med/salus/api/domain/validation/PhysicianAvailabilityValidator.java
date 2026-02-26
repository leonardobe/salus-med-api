package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.ConflictException;
import med.salus.api.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class PhysicianAvailabilityValidator implements AppointmentSchedulingValidator {

    private final AppointmentRepository appointmentRepository;

    public PhysicianAvailabilityValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(AppointmentDTO dto) {
        var scheduleConflict =
                appointmentRepository.existsByPhysicianIdAndAppointmentDate(dto.physicianId(), dto.appointmentDate());

        if (scheduleConflict) {
            throw new ConflictException("Physician already has an appointment scheduled at that time");
        }
    }
}
