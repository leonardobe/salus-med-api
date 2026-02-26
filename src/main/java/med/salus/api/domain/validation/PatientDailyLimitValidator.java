package med.salus.api.domain.validation;

import med.salus.api.domain.valueobject.AppointmentStatus;
import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.ConflictException;
import med.salus.api.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class PatientDailyLimitValidator implements AppointmentSchedulingValidator {
    private final AppointmentRepository repository;

    public PatientDailyLimitValidator(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(AppointmentDTO dto) {

        var hasConflict = repository.existsByPatientIdAndAppointmentDateAndStatus(
                dto.patientId(), dto.appointmentDate(), AppointmentStatus.SCHEDULED);

        if (hasConflict) {
            throw new ConflictException("Patient already has an appointment scheduled for this day");
        }
    }
}
