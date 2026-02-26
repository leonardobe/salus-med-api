package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.UnprocessableEntityException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MinimumAdvanceValidator implements AppointmentSchedulingValidator {
    @Override
    public void validate(AppointmentDTO dto) {
        var dateAppointment = dto.appointmentDate();

        var now = LocalDateTime.now();
        var intervalAppointmentsMin = Duration.between(now, dateAppointment).toMinutes();

        if (intervalAppointmentsMin < 30) {
            throw new UnprocessableEntityException("The medical appointment must be scheduled 30 minutes in advance");
        }
    }
}
