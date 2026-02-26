package med.salus.api.domain.validation;

import med.salus.api.dto.request.AppointmentDTO;
import med.salus.api.exception.BusinessException;
import med.salus.api.exception.UnprocessableEntityException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicBusinessHoursValidator implements AppointmentSchedulingValidator {
    @Override
    public void validate(AppointmentDTO dto) {

        var date = dto.appointmentDate();
        var dayOfWeek = date.getDayOfWeek();
        var hour = date.getHour();

        var sunday = dayOfWeek.equals(DayOfWeek.SUNDAY);
        var beforeOpening = hour < 7;
        var afterClosing = hour > 18;

        if (sunday || beforeOpening || afterClosing) {
            throw new UnprocessableEntityException("Appointment outside clinic business hours");
        }
    }
}
