package med.salus.api.mapper;

import med.salus.api.domain.entity.Appointment;
import med.salus.api.dto.response.AppointmentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPhysician().getId(),
                appointment.getPatient().getId(),
                appointment.getAppointmentDate());
    }
}
