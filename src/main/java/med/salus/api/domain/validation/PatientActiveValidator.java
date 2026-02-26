package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.ConflictException;
import med.salus.api.service.PatientService;
import org.springframework.stereotype.Component;

@Component
public class PatientActiveValidator implements AppointmentSchedulingValidator {

    private final PatientService service;

    public PatientActiveValidator(PatientService service) {
        this.service = service;
    }

    public void validate(AppointmentDTO dto) {

        if (dto.patientId() == null) {
            return;
        }

        var isActivePatient = service.isActivePatient(dto.patientId());

        if (!isActivePatient) {
            throw new ConflictException("Appointments cannot be scheduled for inactive patients");
        }
    }
}
