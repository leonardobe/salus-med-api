package med.salus.api.mapper;

import med.salus.api.domain.entity.Appointment;
import med.salus.api.dto.response.CancelAppointmentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CancelMapper {

    public CancelAppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return new CancelAppointmentResponseDTO(
                appointment.getId(),
                appointment.getStatus(),
                appointment.getCancellationReason(),
                appointment.getCancellationDate());
    }
}
