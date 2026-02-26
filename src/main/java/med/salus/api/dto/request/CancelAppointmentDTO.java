package med.salus.api.dto.request;

import jakarta.validation.constraints.NotNull;
import med.salus.api.domain.valueobject.CancellationReason;

public record CancelAppointmentDTO(@NotNull CancellationReason reason) {}
