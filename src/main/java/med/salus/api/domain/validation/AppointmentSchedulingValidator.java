package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;

public interface AppointmentSchedulingValidator {
    void validate(AppointmentDTO dto);
}
