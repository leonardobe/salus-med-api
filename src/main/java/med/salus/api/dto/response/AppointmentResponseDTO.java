package med.salus.api.dto.response;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id, Long physicianId, Long patientId, LocalDateTime appointmentDate) {}
