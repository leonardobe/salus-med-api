package med.salus.api.dto.response;

import med.salus.api.domain.valueobject.AppointmentStatus;
import med.salus.api.domain.valueobject.CancellationReason;

import java.time.LocalDateTime;

public record CancelAppointmentResponseDTO(
        Long id, AppointmentStatus status, CancellationReason cancellationReason, LocalDateTime cancellationDate) {}
